package com.levi9.demo.wallet.service.filter;

import com.levi9.demo.wallet.entity.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@RequiredArgsConstructor
public class CriteriaFilterParser {

    private final Root<Transaction> root;

    private final CriteriaBuilder cb;

    public Predicate parse(String filter) {
        var stopWatch = new StopWatch();
        stopWatch.start();
        if (StringUtils.isEmpty(filter)) {
            return cb.and();
        }
        var lexer = new FilterLexer(CharStreams.fromString(filter));
        var parser = new FilterParser(new CommonTokenStream(lexer));
        var visitor = new FilterToPredicateVisitor(root, cb);
        var predicate = (Predicate) visitor.visit(parser.parse());
        stopWatch.stop();
        log.info("Parsed '{}' in {}ms", filter, stopWatch.getTotalTimeMillis());
        return predicate;
    }
}
