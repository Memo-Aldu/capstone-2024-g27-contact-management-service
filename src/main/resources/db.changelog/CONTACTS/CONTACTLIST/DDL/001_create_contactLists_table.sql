contacts-service-main/
│
├── models/
│   ├── dto/
│   │   ├── ContactDTO.java
│   │   └── ContactRootDTO.java
│   │
│   ├── dto/deserializers/
│   │   ├── ContactEntityListDeserializer.java
│   │   └── ContactDtoListDeserializer.java
│   │
│   ├── dto/schemas/
│   │   └── NestedContactSchema.java
│   │
│   ├── entity/
│   │   ├── ContactEntity.java
│   │   └── ContactMetadata.java
│   │
│   └── queries/
│       ├── ContactFilterRequestDTO.java
│       ├── ContactFilterDTO.java
│       ├── ContactFilterBO.java
│       └── ContactFilterRequestBO.java
│
├── persistence/
│   ├── ContactCriteriaRepository.java
│   └── IContactRepository.java
│
├── mappers/
│   ├── IContactSearchMapper.java
│   └── IContactMapper.java
│   │
│   └── transformers/
│       └── ContactTupleTransformer.java
│
├── services/
│   ├── ContactCrudService.java
│   └── ContactSearchService.java
│
└── rest/
├── ContactSearchController.java
├── ContactCrudController.java
│
    └── constants/
└── ContactControllerConstants.java
