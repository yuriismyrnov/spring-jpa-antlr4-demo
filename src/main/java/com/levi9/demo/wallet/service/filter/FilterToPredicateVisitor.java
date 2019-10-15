package com.levi9.demo.wallet.service.filter;

import com.levi9.demo.wallet.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.ErrorNode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@RequiredArgsConstructor
public class FilterToPredicateVisitor extends FilterBaseVisitor {

    private final Root<Transaction> root;

    private final CriteriaBuilder cb;

    @Override
    public Object visitParse(FilterParser.ParseContext ctx) {
        return ctx.predicate().accept(this);
    }

    @Override
    public Object visitOrPredicate(FilterParser.OrPredicateContext ctx) {
        Predicate[] predicates = ctx.predicate().stream()
                .map(child -> (Predicate) child.accept(this))
                .toArray(Predicate[]::new);
        return cb.or(predicates);
    }

    @Override
    public Object visitAndPredicate(FilterParser.AndPredicateContext ctx) {
        Predicate[] predicates = ctx.predicate().stream()
                .map(child -> (Predicate) child.accept(this))
                .toArray(Predicate[]::new);
        return cb.and(predicates);
    }

    @Override
    public Object visitNullnessPredicate(FilterParser.NullnessPredicateContext ctx) {
        var expression = (Path<?>) ctx.path_expression().accept(this);
        if (ctx.operation.NULL() != null) {
            return expression.isNull();
        } else {
            return expression.isNotNull();
        }
    }

    @Override
    public Object visitNotPredicate(FilterParser.NotPredicateContext ctx) {
        var predicate = (Predicate) ctx.predicate().accept(this);
        return cb.not(predicate);
    }

    @Override
    public Object visitParenthesesPredicate(FilterParser.ParenthesesPredicateContext ctx) {
        return ctx.predicate().accept(this);
    }

    @Override
    public Object visitComparisonPredicate(FilterParser.ComparisonPredicateContext ctx) {
        var expression = (Expression<?>) ctx.any_expression().accept(this);
        String operation = ctx.operation.getText();
        Object value = ctx.value().accept(this);
        if (ctx.operation.EQ() != null) {
            return cb.equal(expression, value);
        }
        if (ctx.operation.NE() != null) {
            return cb.notEqual(expression, value);
        }
        if (ctx.operation.GT() != null) {
            return cb.greaterThan((Expression<Comparable>) expression, (Comparable) value);
        }
        if (ctx.operation.GE() != null) {
            return cb.greaterThanOrEqualTo((Expression<Comparable>) expression, (Comparable) value);
        }
        if (ctx.operation.LT() != null) {
            return cb.lessThan((Expression<Comparable>) expression, (Comparable) value);
        }
        if (ctx.operation.LE() != null) {
            return cb.lessThanOrEqualTo((Expression<Comparable>) expression, (Comparable) value);
        }
        throw new IllegalArgumentException("Operation is not supported: " + operation);
    }

    @Override
    public Object visitLength_expression(FilterParser.Length_expressionContext ctx) {
        var expression = (Expression) ctx.path_expression().accept(this);
        if (expression.getJavaType() != String.class) {
            throw new IllegalArgumentException("Cannot execute length() on a non-textual field");
        }
        return cb.length((Expression<String>) expression);
    }

    @Override
    public Object visitPath_expression(FilterParser.Path_expressionContext ctx) {
        String[] fields = ctx.PATH().getText().split("\\.");
        From<?, ?> path = root;
        for (int i = 0; i < fields.length - 1; i++) {
            path = path.join(fields[i], JoinType.LEFT);
        }
        return path.get(fields[fields.length - 1]);
    }

    @Override
    public Object visitStringValue(FilterParser.StringValueContext ctx) {
        String text = ctx.getText();
        if (!text.startsWith("'") && !text.endsWith("'")) {
            throw new IllegalStateException("Expecting string values to be marked by apostrophes");
        }
        return text.substring(1, text.length() - 1);
    }

    @Override
    public Object visitDateValue(FilterParser.DateValueContext ctx) {
        return LocalDate.parse(ctx.getText());
    }

    @Override
    public Object visitDateTimeValue(FilterParser.DateTimeValueContext ctx) {
        return Instant.parse(ctx.getText());
    }

    @Override
    public Object visitIntegerValue(FilterParser.IntegerValueContext ctx) {
        return Integer.parseInt(ctx.getText());
    }

    @Override
    public Object visitDecimalValue(FilterParser.DecimalValueContext ctx) {
        return new BigDecimal(ctx.getText());
    }

    @Override
    public Object visitBooleanValue(FilterParser.BooleanValueContext ctx) {
        return Boolean.parseBoolean(ctx.getText());
    }

    @Override
    public Object visitErrorNode(ErrorNode node) {
        throw new IllegalArgumentException("Encountered an error while parsing filter request");
    }
}
