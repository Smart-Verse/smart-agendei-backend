package com.smartverse.agendeibackend_gen;

import com.potatotech.authorization.exception.ServiceException;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class SpecificationFilter<T> {

        public Specification<T> customFilter(String filter){
            Specification<T> spec = Specification.where(null);
            if(filter == null || filter.isEmpty()){
                return spec;
            }

            return (root, query, cb) -> {
                try{
                    if (filter == null || filter.isEmpty()) {
                        return null;
                    }

                    return parseExpression(filter, cb, root);
                } catch (Exception ex){
                    throw new ServiceException(HttpStatus.BAD_REQUEST,"Invalid filter: " + filter);
                }
            };
        }


        private Predicate parseExpression(String expression, CriteriaBuilder cb, Root<?> root) {

            expression = expression.trim();


            if (expression.startsWith("(") && expression.endsWith(")")) {
                return parseExpression(expression.substring(1, expression.length() - 1), cb, root);
            }


            String[] andParts = expression.split("(?i)\\s+and\\s+");
            if (andParts.length > 1) {
                Predicate andPredicate = cb.conjunction();
                for (String part : andParts) {
                    andPredicate = cb.and(andPredicate, parseExpression(part, cb, root));
                }
                return andPredicate;
            }


            String[] orParts = expression.split("(?i)\\s+or\\s+");
            if (orParts.length > 1) {
                Predicate orPredicate = cb.disjunction();
                for (String part : orParts) {
                    orPredicate = cb.or(orPredicate, parseExpression(part, cb, root));
                }
                return orPredicate;
            }

            if(expression.trim().contains("eq")){
                return setEq(cb, expression.trim(), (Root<T>) root);
            }
            else if(expression.trim().contains("isNull")){
                return setIsNull(cb, expression.trim(), (Root<T>) root);
            }
            else if(expression.trim().contains("notNull")){
                return setNotNull(cb, expression.trim(), (Root<T>) root);
            } else {
                throw new ServiceException(HttpStatus.BAD_REQUEST,"Invalid expression: " + expression);
            }
        }

        private Predicate setEq(CriteriaBuilder cb, String item, Root<T> root) {

            var criteria = item.split("eq");
            if(criteria.length == 2){
                var hasJoin = criteria[0].split("\\.");
                if(hasJoin.length > 1){

                    var join = createDynamicJoin(root, hasJoin);

                    String finalProperty = hasJoin[hasJoin.length - 1];

                    if (UUID.class.isAssignableFrom(join.get(finalProperty.trim()).getJavaType())){
                        return cb.equal(join.get(finalProperty.trim()), UUID.fromString(criteria[1].trim()));
                    }else {
                        return cb.like(cb.lower(join.get(finalProperty.trim())), "%".concat(criteria[1].trim()).concat("%").toLowerCase());
                    }
                } else {
                    if (UUID.class.isAssignableFrom(root.get(criteria[0].trim()).getJavaType())){
                        return cb.equal(root.get(criteria[0].trim()), UUID.fromString(criteria[1].trim()));
                    }
                    else if(root.get(criteria[0].trim()).getJavaType().isEnum()){
                        return cb.equal(root.get(criteria[0].trim()), criteria[1].trim());
                    }
                    else {
                        return cb.like(cb.lower(root.get(criteria[0].trim())), "%".concat(criteria[1].trim()).concat("%").toLowerCase());
                    }
                }

            }
            return null;
        }

        private Predicate setIsNull(CriteriaBuilder cb, String item, Root<T> root) {

            var criteria = item.split("isNull");
            if(criteria.length == 1){
                var hasJoin = criteria[0].split("\\.");
                if(hasJoin.length > 1){

                    var join = createDynamicJoin(root, hasJoin);

                    String finalProperty = hasJoin[hasJoin.length - 1];

                    return cb.isNull(join.get(finalProperty.trim()));

                } else {
                    return cb.isNull(root.get(criteria[0].trim()));
                }
            }
            return null;
        }

        private Predicate setNotNull(CriteriaBuilder cb, String item, Root<T> root) {

            var criteria = item.split("notNull");
            if(criteria.length == 1){
                var hasJoin = criteria[0].split("\\.");
                if(hasJoin.length > 1){

                    var join = createDynamicJoin(root, hasJoin);

                    String finalProperty = hasJoin[hasJoin.length - 1];

                    return cb.isNotNull(join.get(finalProperty.trim()));

                } else {
                    return cb.isNotNull(root.get(criteria[0].trim()));
                }
            }
            return null;
        }

        private static Join<?, ?> createDynamicJoin(From<?, ?> root, String[] properties){
            var currentJoin = root;
            for (int i = 0; i < properties.length - 1; i++) {
                currentJoin = currentJoin.join(properties[i]);
            }

            return (Join<?, ?>) currentJoin;
        }

        public static String displayFieldsEntity(String displayFields,String entityName){
            if(displayFields.equals("*")){
                return "*";
            } else{

                Pattern pattern = Pattern.compile("\\b"+entityName+"(?:\\.[a-zA-Z_]+)+\\b");
                Matcher matcher = pattern.matcher(displayFields);
                var matches = new ArrayList<>();

                while (matcher.find()) {
                    matches.add(matcher.group());
                }

                var display = new AtomicReference<>("");

                matches.forEach(e -> {
                    display.set(String.format("%s%s;",display.get(),e.toString().replace(entityName+".","")));
                });

                return display.get().contains("*") ? "*" : display.get();
            }
        }


}
