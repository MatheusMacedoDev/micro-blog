# Micro Blog
Uma pequena API de um blog site.

## Diagrama Entidade Relacionamento (DER)
```mermaid
erDiagram
    AUTHOR ||--o{ POST : writes

    AUTHOR {
        integer id
        varchar name
    }

    POST {
        integer id
        varchar title
        text content
        timestamp published_at
    }
```
