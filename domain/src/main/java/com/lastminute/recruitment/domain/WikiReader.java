package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.net.MalformedURLException;

public interface WikiReader {

    WikiPage read(String link) throws WikiPageNotFound, MalformedURLException;

}
