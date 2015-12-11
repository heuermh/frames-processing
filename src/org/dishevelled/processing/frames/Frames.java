/*

    Frames animation library for Processing.
    Copyright (c) 2012-2013 held jointly by the individual authors.

    This file is part of Frames animation library for Processing.

    Frames animation library for Processing is free software: you can redistribute it and/or
    modify it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Frames animation library for Processing is distributed in the hope that it will be
    useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Frames animation library for Processing.  If not, see
    <http://www.gnu.org/licenses/>.

*/
package org.dishevelled.processing.frames;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Frames.
 *
 * @author  Michael Heuer
 */
public final class Frames
{
    private final PApplet applet;

    public Frames(final PApplet applet)
    {
        checkNotNull(applet, "applet must not be null");
        this.applet = applet;
    }

    public SingleFrameAnimation createAnimation(final String imageName)
    {
        checkNotNull(imageName, "imageName must not be null");
        return createAnimation(applet.loadImage(imageName));
    }

    public SingleFrameAnimation createAnimation(final PImage image)
    {
        checkNotNull(image, "image must not be null");
        return new SingleFrameAnimation(image);
    }

    public MultipleFramesAnimation createAnimation(final String imageName, final String suffix, final int frames)
    {
        return createAnimation(createFrameList(imageName, suffix, frames));
    }

    public LoopedFramesAnimation createLoopedAnimation(final String imageName, final String suffix, final int frames)
    {
        return createLoopedAnimation(createFrameList(imageName, suffix, frames));
    }

    public MultipleFramesAnimation createAnimation(final String imageName, final int x, final int y,
                                                   final int width, final int height, final int frames)
    {
        checkNotNull(imageName, "imageName must not be null");
        return createAnimation(applet.loadImage(imageName), x, y, width, height, frames);
    }

    public MultipleFramesAnimation createAnimation(final PImage image, final int x, final int y,
                                                   final int width, final int height, final int frames)
    {
        return createAnimation(createFrameList(image, x, y, width, height, frames));
    }

    public LoopedFramesAnimation createLoopedAnimation(final String imageName, final int x, final int y,
                                                       final int width, final int height, final int frames)
    {
        checkNotNull(imageName, "imageName must not be null");
        return createLoopedAnimation(applet.loadImage(imageName), x, y, width, height, frames);
    }

    public LoopedFramesAnimation createLoopedAnimation(final PImage image, final int x, final int y,
                                                       final int width, final int height, final int frames)
    {
        return createLoopedAnimation(createFrameList(image, x, y, width, height, frames));
    }

    public MultipleFramesAnimation createAnimation(final List<PImage> images)
    {
        checkNotNull(images, "images must not be null");
        return new MultipleFramesAnimation(images);
    }

    public LoopedFramesAnimation createLoopedAnimation(final List<PImage> images)
    {
        checkNotNull(images, "images must not be null");
        return new LoopedFramesAnimation(images);
    }

    /**
     * @since 1.1
     */
    public MultipleFramesAnimation createAnimation(final PImage... images)
    {
        checkNotNull(images, "images must not be null");
        return createAnimation(Arrays.asList(images));
    }

    /**
     * @since 1.1
     */
    public LoopedFramesAnimation createLoopedAnimation(final PImage... images)
    {
        checkNotNull(images, "images must not be null");
        return createLoopedAnimation(Arrays.asList(images));
    }

    public List<PImage> createFrameList(final String imageName, final String suffix, final int frames)
    {
        checkNotNull(imageName, "imageName must not be null");
        checkNotNull(suffix, "suffix must not be null");
        if (frames < 1) {
            throw new IllegalArgumentException("frames must be at least 1");
        }
        int leadingZeros = (int) (frames / 10) + 1; // is this math correct?
        String format = "%s%0" + leadingZeros + "d%s";
        List<PImage> images = new ArrayList<PImage>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            PImage image = applet.loadImage(String.format(format, new Object[] { imageName, frame, suffix }));
            images.add(image);
        }
        return Collections.unmodifiableList(images);
    }

    public List<PImage> createFrameList(final String imageName, final int x, final int y,
                                        final int width, final int height, final int frames)
    {
        checkNotNull(imageName, "imageName must not be null");
        return createFrameList(applet.loadImage(imageName), x, y, width, height, frames);
    }

    public List<PImage> createFrameList(final PImage image, final int x, final int y,
                                        final int width, final int height, final int frames)
    {
        checkNotNull(image, "image must not be null");
        if (x < 0) {
            throw new IllegalArgumentException("x must be at least 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y must be at least 0");
        }
        if (width < 0) {
            throw new IllegalArgumentException("width must be at least 0");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must be at least 0");
        }
        if (frames < 1) {
            throw new IllegalArgumentException("frames must be at least 1");
        }
        List<PImage> images = new ArrayList<PImage>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            PImage subimage = new PImage(((BufferedImage) image.getImage()).getSubimage(x + (frame * width), y, width, height));
            images.add(subimage);
        }
        return Collections.unmodifiableList(images);
    }

    public PImage createSpriteSheet(final List<PImage> frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        int width = 0;
        int height = 0;
        for (PImage image : frameImages)
        {
            if (image.width > width)
            {
                width = image.width;
            }
            if (image.height > height)
            {
                height = image.height;
            }
        }
        BufferedImage spriteSheet = new BufferedImage(width * frameImages.size(), height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = spriteSheet.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        for (int i = 0, size = frameImages.size(); i < size; i++)
        {
            PImage image = frameImages.get(i);
            int x = width * i + (width / 2) - (image.width / 2);
            int y = (height / 2) - (image.height / 2);
            graphics.drawImage(image.getImage(), x, y, null);
        }
        graphics.dispose();
        return new PImage(spriteSheet);
    }

    /**
     * @since 1.1
     */
    public PImage createSpriteSheet(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return createSpriteSheet(Arrays.asList(frameImages));
    }

    public PImage createSpriteSheet(final String imageName, final String suffix, final int frames)
    {
        return createSpriteSheet(createFrameList(imageName, suffix, frames));
    }

    public PImage flipHorizontally(final PImage image)
    {
        checkNotNull(image, "image must not be null");
        PImage flipped = new PImage(image.width, image.height, PImage.ARGB);
        for (int i = 0; i < image.width; i++)
        {
            for (int j = 0; j < image.height; j++)
            {
                flipped.set(image.width - 1 - i, j, image.get(i, j));
            }
        }
        return flipped;
    }

    public List<PImage> flipHorizontally(final List<PImage> frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        List<PImage> flippedFrames = new ArrayList<PImage>(frameImages.size());
        for (PImage frame : frameImages)
        {
            flippedFrames.add(flipHorizontally(frame));
        }
        return flippedFrames;
    }

    /**
     * @since 1.1
     */
    public List<PImage> flipHorizontally(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return flipHorizontally(Arrays.asList(frameImages));
    }

    public PImage flipVertically(final PImage image)
    {
        checkNotNull(image, "image must not be null");
        PImage flipped = new PImage(image.width, image.height, PImage.ARGB);
        for (int i = 0; i < image.width; i++)
        {
            for (int j = 0; j < image.height; j++)
            {
                flipped.set(i, image.height - 1 - j, image.get(i, j));
            }
        }
        return flipped;
    }

    public List<PImage> flipVertically(final List<PImage> frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        List<PImage> flippedFrames = new ArrayList<PImage>(frameImages.size());
        for (PImage frame : frameImages)
        {
            flippedFrames.add(flipVertically(frame));
        }
        return flippedFrames;
    }

    /**
     * @since 1.1
     */
    public List<PImage> flipVertically(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return flipVertically(Arrays.asList(frameImages));
    }

    public List<PImage> rotate(final PImage image, final int steps)
    {
        checkNotNull(image, "image must not be null");
        if (steps < 1)
        {
            throw new IllegalArgumentException("steps must be at least 1");
        }

        int width = image.width;
        int height = image.height;
        int size = Math.max(width, height);
        BufferedImage spriteSheet = new BufferedImage(size * steps, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = spriteSheet.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        double x = width / 2.0d;
        double y = height / 2.0d;
        double r = (2.0d / (double) steps) * Math.PI;
        for (int i = 0; i < steps; i++)
        {
            AffineTransform rotate = new AffineTransform();
            rotate.translate(size * i, 0.0d);
            rotate.rotate(i * r, x, y);
            graphics.drawRenderedImage(((BufferedImage) image.getImage()), rotate);
        }
        graphics.dispose();
        return createFrameList(new PImage(spriteSheet), 0, 0, size, size, steps);
    }

    private static void checkNotNull(final Object value, final String message)
    {
        if (value == null)
        {
            throw new NullPointerException(message);
        }
    }
}
