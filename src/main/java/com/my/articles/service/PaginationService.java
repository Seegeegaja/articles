package com.my.articles.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    //페이지당 보여질 페이지 수
    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumber(
            int currentPageNumber,
            int totalPageNumber
    ) {
        int startPageNumber =
                Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber =
                Math.min(startPageNumber + BAR_LENGTH, totalPageNumber);
        return IntStream.range(startPageNumber, endNumber).boxed().toList();
    }
    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
