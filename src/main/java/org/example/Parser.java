package org.example;

import java.io.IOException;

public interface Parser {

    String getContentWithoutUnicode() throws IOException;

    String getMoreContent();

    boolean isContentCorrect();

    String getContent()  throws IOException;

    boolean assertConsistency();
}
