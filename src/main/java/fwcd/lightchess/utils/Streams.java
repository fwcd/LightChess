package fwcd.lightchess.utils;

import java.util.Optional;
import java.util.stream.Stream;

public final class Streams {
	private Streams() {}
	
	@SafeVarargs
	public static <T> Stream<T> merge(Stream<T>... streams) {
		return Stream.of(streams).flatMap(it -> it);
	}
	
	public static <T> Stream<T> filterPresent(Stream<Optional<T>> stream) {
		return stream.filter(Optional::isPresent).map(it -> it.orElse(null));
	}
	
	@SafeVarargs
	public static <T> Stream<T> present(Optional<T>... values) {
		return filterPresent(Stream.of(values));
	}
}
