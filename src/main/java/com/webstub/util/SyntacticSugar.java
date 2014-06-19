package com.webstub.util;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: skrymer
 * Date: 1/06/14
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class SyntacticSugar {

    public static <T> List<T> safe( List<T> other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }
}
