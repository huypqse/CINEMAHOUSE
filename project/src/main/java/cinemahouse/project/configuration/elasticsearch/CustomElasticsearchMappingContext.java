//package cinemahouse.project.configuration.elasticsearch;
//
//import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
//import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
//import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
//import org.springframework.data.mapping.model.Property;
//import org.springframework.data.mapping.model.SimpleTypeHolder;
//import org.springframework.lang.NonNull;
//
///**
// * Custom mapping context in order to use the same entities for both MySQL and Elasticsearch datasources
// */
//public class CustomElasticsearchMappingContext extends SimpleElasticsearchMappingContext {
//
//    @NonNull
//    @Override
//    protected ElasticsearchPersistentProperty createPersistentProperty(
//            @NonNull Property property,
//            @NonNull SimpleElasticsearchPersistentEntity owner,
//            @NonNull SimpleTypeHolder simpleTypeHolder
//    ) {
//        return new CustomElasticsearchPersistentProperty(property, owner, simpleTypeHolder);
//    }
//}
//
