package fwcd.lightchess.utils;

import fwcd.fructose.Option;
import java.util.stream.Stream;

public final class Streams {
	private Streams() {}
	
	@SafeVarargs
	public static <T> Stream<T> merge(Stream<T>... streams) {
		return Stream.of(streams).flatMap(it -> it);
	}
	
	public static <T> Stream<T> filterPresent(Stream<Option<T>> stream) {
		return stream.filter(Option::isPresent).map(it -> it.orElse(null));
	}
	
	@SafeVarargs
	public static <T> Stream<T> present(Option<T>... values) {
		return filterPresent(Stream.of(values));
	}
}
