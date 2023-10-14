package com.webtoon.coding.mock.args.comment;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CommentVerifyFailCommentArgs implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(Arguments.of((Object) null), Arguments.of(""), Arguments.of("hello^^"),
                Arguments.of("he!!llo"), Arguments.of("!!hello"));
    }

}
