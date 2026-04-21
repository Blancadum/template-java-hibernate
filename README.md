# HibernateDemo — Plantilla Hibernate + PostgreSQL

Plantilla base per a projectes Java amb **Hibernate 6** i **PostgreSQL** (IOC DAM/DAW).

Inclou la configuració mínima necessària i un DAO complet amb tots els mètodes CRUD per copiar i adaptar a cada projecte.

---

## Estructura del projecte

```
HibernateDemo/
├── sql/
│   └── setup.sql                        ← Script per crear usuari i BD a PostgreSQL
├── src/main/
│   ├── java/cat/exemple/
│   │   ├── Application.java             ← Exemple d'ús
│   │   ├── model/
│   │   │   └── Producte.java            ← Entitat JPA d'exemple
│   │   ├── dao/
│   │   │   └── ProducteDAO.java         ← DAO complet (plantilla CRUD)
│   │   └── util/
│   │       └── HibernateUtil.java       ← Connexió a la BD (Singleton)
│   └── resources/
│       └── hibernate.cfg.xml            ← Configuració de Hibernate
└── pom.xml                              ← Dependències Maven
```

---

## 1. Setup de PostgreSQL

Només cal fer-ho una vegada per màquina.

**Opció A — amb el script:**
```bash
psql postgres -f sql/setup.sql
```

**Opció B — manualment:**
```bash
psql postgres
```
```sql
CREATE USER hibuser WITH PASSWORD 'password';
CREATE DATABASE hibernatedb OWNER hibuser;
GRANT ALL PRIVILEGES ON DATABASE hibernatedb TO hibuser;
\q
```

**Verificar que funciona:**
```bash
psql -U hibuser -d hibernatedb
```
Ha de connectar sense errors.

---

## 2. Configuració de Hibernate

El fitxer `src/main/resources/hibernate.cfg.xml` ja té la configuració per defecte:

| Propietat | Valor |
|-----------|-------|
| Host | `localhost:5432` |
| Base de dades | `hibernatedb` |
| Usuari | `hibuser` |
| Contrasenya | `password` |
| `hbm2ddl.auto` | `update` (crea/actualitza taules automàticament) |

---

## 3. Adaptar el DAO al teu projecte

`ProducteDAO.java` és la plantilla. Per adaptar-la a la teva entitat:

**Pas 1** — Canvia `Producte` pel nom de la teva classe:
```java
// Abans:
public Long save(Producte producte) { ... }

// Després (exemple amb Event):
public Long save(Event event) { ... }
```

**Pas 2** — Canvia el nom de la variable:
```java
session.persist(producte);  →  session.persist(event);
session.get(Producte.class, id);  →  session.get(Event.class, id);
```

**Pas 3** — Afegeix els `findBy` que necessitis seguint el mateix patró:
```java
public List<Event> findByType(String type) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        return session.createQuery(
            "FROM Event e WHERE LOWER(e.type) LIKE LOWER(:type)", Event.class)
            .setParameter("type", "%" + type + "%")
            .list();
    }
}
```

---

## 4. Mètodes CRUD disponibles al DAO

| Mètode | SQL equivalent | Quan usar-lo |
|--------|---------------|--------------|
| `save(obj)` | `INSERT` | Guardar objecte nou |
| `getById(id)` | `SELECT WHERE id=?` | Buscar per clau primària |
| `getAll()` | `SELECT *` | Obtenir tots els registres |
| `update(obj)` | `UPDATE` | Modificar objecte existent |
| `delete(obj)` | `DELETE` | Esborrar objecte |
| `findByNom(text)` | `SELECT WHERE nom LIKE %?%` | Cerca parcial per nom |

---

## 5. Executar el projecte

```bash
mvn compile exec:java
```
