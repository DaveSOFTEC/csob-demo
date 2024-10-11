package cz.csob.demo.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {

    public static <T> Stream<T> streamOfNullable(Collection<T> list) {
        return Optional.ofNullable(list).stream().flatMap(Collection::stream);
    }

    public static <T> void forEachNullable(List<T> list, Consumer<T> function) {
        streamOfNullable(list).forEach(function);
    }
}
