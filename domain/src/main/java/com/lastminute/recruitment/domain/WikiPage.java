package com.lastminute.recruitment.domain;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class WikiPage {
    private final String title;
    private final String content;
    private final String selfLink;
    private final List<String> links;
}
