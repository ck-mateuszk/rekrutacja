package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

public interface WikiReader {

    WikiPage read(String link) throws WikiPageNotFound;

}
