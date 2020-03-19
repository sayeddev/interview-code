package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 *
 * Inspired by: https://github.com/yegor256/quiz/blob/master/Parser.java
 */
public class ParserImpl implements Parser {
    private File file;
    public static final int LIMIT = 0x80;

    public synchronized void setFile(final File file) {
        this.file = file;
    }

    /**
     * This returns the file.
     *
     * @return File
     */
    public synchronized File getFile() {
        return this.file;
    }


    @Override
    public String getContent() throws IOException {
        final FileInputStream fis = new FileInputStream(this.file);
        final StringBuilder output = new StringBuilder();
        int data;
        try {
            while ((data = fis.read()) > 0) {
                output.append((char) data);
            }
        } finally {
            fis.close();
        }
        return output.toString();
    }

    public String getContentWithoutUnicode() throws IOException, IllegalStateException {
        final FileInputStream fis = new FileInputStream(this.file);
        final StringBuilder output = new StringBuilder();
        try {
            int data;
            while ((data = fis.read()) > 0) {
                if (data < LIMIT) {
                    output.append((char) data);
                }
                if (data == 1) {
                    throw new IllegalStateException("this should never happen with ascii text");
                }
            }
        }finally{
            fis.close();
        }
        return output.toString();
    }

    /**
     * @return String
     */
    @Override
    public String getMoreContent() {
        return null;
    }

    @Override
    public boolean isContentCorrect() {
        return false;
    }

    @Override
    public boolean assertConsistency() {
        throw new IllegalStateException("not yet implemented");
    }

    public void saveContent(final String content) throws IOException {
        final FileOutputStream fos = new FileOutputStream(this.file);
        try {
            for (int index = 0; index < content.length(); index += 1) {
                fos.write(content.charAt(index));
            }
        } finally {
            fos.close();
        }
    }
}
