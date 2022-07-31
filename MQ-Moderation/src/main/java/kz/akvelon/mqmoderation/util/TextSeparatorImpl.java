package kz.akvelon.mqmoderation.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TextSeparatorImpl implements TextSeparator{
    @Override
    public List<String> separate(String text) {
        text = text.replaceAll("[.,{}!?*^]","");
        return Arrays.asList(text.split(" "));
    }
}
