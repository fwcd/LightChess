package com.fwcd.lightchess.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import com.fwcd.fructose.io.InputStreamable;

public class ImageLoader {
	private final Map<String, BufferedImage> cache = new HashMap<>();
	
	public BufferedImage load(String name, Supplier<InputStreamable> source) {
		BufferedImage img = cache.get(name);
		if (img == null) {
			img = source.get().mapStream(ImageIO::read);
			cache.put(name, img);
		}
		return img;
	}
}
