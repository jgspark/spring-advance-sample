package com.webtoon.coding.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(
        value = {
                SpringExtension.class,
//                RestDocumentationExtension.class
//        }
        }
)
//@Import(RestDocsTestConfiguration.class)
public class BaseTestController {

    @Autowired
    protected MockMvc mockMvc;

//    @Autowired
//    protected RestDocumentationResultHandler restDocs;
//
//    @BeforeEach
//    void setUp(final WebApplicationContext context,
//               final RestDocumentationContextProvider provider) {
//        this.mockMvc = webAppContextSetup(context)
//                .apply(
//                        documentationConfiguration(provider).uris()
//                                .withScheme("http")
//                                .withHost("localhost")
//                                .withPort(8080)
//                )
//                .alwaysDo(print())
//                .alwaysDo(restDocs)
//                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
//                .build();
//    }


    @BeforeEach
    void setUp(final WebApplicationContext context) {
        this.mockMvc = webAppContextSetup(context)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                .build();
    }

}

