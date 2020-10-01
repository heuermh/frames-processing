/*

    Frames animation library for Processing.
    Copyright (c) 2012-2020 held jointly by the individual authors.

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
    /** Applet for this frames library. */
    private final PApplet applet;


    /**
     * Create a new frames library for the specified applet.
     *
     * @applet applet, must not be null
     */
    public Frames(final PApplet applet)
    {
        checkNotNull(applet, "applet must not be null");
        this.applet = applet;
    }

    /**
     * Create and return a new single frame animation from the specified image file name.
     *
     * @param imageName image file name, must not be null
     * @return a new single frame animation from the specified image file name
     */
    public SingleFrameAnimation createAnimation(final String imageName)
    {
        checkNotNull(imageName, "imageName must not be null");
        return createAnimation(applet.loadImage(imageName));
    }

    /**
     * Create and return a new single frame animation from the specified image.
     *
     * @param image image, must not be null
     * @return a new single frame animation from the specified image
     */
    public SingleFrameAnimation createAnimation(final PImage image)
    {
        checkNotNull(image, "image must not be null");
        return new SingleFrameAnimation(image);
    }

    /**
     * Create and return a new multiple frames animation containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file or URL name, must not be null
     * @param suffix image suffix, must not be null
     * @param frames number of frames, must be at least one
     * @return a new multiple frames animation containing all the frame images
     *    specified from <code>baseImage</code>
     */
    public MultipleFramesAnimation createAnimation(final String baseImage, final String suffix, final int frames)
    {
        return createAnimation(createFrameList(baseImage, suffix, frames));
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file or URL name, must not be null
     * @param suffix image suffix, must not be null
     * @param frames number of frames, must be at least one
     * @return a new looped frames animation containing all the frame images
     *    specified from <code>baseImage</code>
     */
    public LoopedFramesAnimation createLoopedAnimation(final String baseImage, final String suffix, final int frames)
    {
        return createLoopedAnimation(createFrameList(baseImage, suffix, frames));
    }

    /**
     * Create and return a new multiple frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image name, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new multiple frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public MultipleFramesAnimation createAnimation(final String spriteSheet, final int x, final int y,
                                                   final int width, final int height, final int frames)
    {
        checkNotNull(spriteSheet, "spriteSheet must not be null");
        return createAnimation(applet.loadImage(spriteSheet), x, y, width, height, frames);
    }

    /**
     * Create and return a new multiple frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new multiple frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public MultipleFramesAnimation createAnimation(final PImage spriteSheet, final int x, final int y,
                                                   final int width, final int height, final int frames)
    {
        return createAnimation(createFrameList(spriteSheet, x, y, width, height, frames));
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image name, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public LoopedFramesAnimation createLoopedAnimation(final String spriteSheet, final int x, final int y,
                                                       final int width, final int height, final int frames)
    {
        checkNotNull(spriteSheet, "spriteSheet must not be null");
        return createLoopedAnimation(applet.loadImage(spriteSheet), x, y, width, height, frames);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public LoopedFramesAnimation createLoopedAnimation(final PImage spriteSheet, final int x, final int y,
                                                       final int width, final int height, final int frames)
    {
        return createLoopedAnimation(createFrameList(spriteSheet, x, y, width, height, frames));
    }

    /**
     * Create and return a new multiple frames animation containing the specified frame images.
     *
     * @param images list of frame images, must not be null
     * @return a new multiple frames animation containing the specified frame images
     */
    public MultipleFramesAnimation createAnimation(final List<PImage> images)
    {
        checkNotNull(images, "images must not be null");
        return new MultipleFramesAnimation(images);
    }

    /**
     * Create and return a new looped frames animation containing the specified frame images.
     *
     * @param images list of frame images, must not be null
     * @return a new looped frames animation containing the specified frame images
     */
    public LoopedFramesAnimation createLoopedAnimation(final List<PImage> images)
    {
        checkNotNull(images, "images must not be null");
        return new LoopedFramesAnimation(images);
    }

    /**
     * Create and return a new multiple frames animation containing the specified frame images.
     *
     * @since 1.1
     * @param images one or more frame images, must not be null
     * @return a new multiple frames animation containing the specified frame images
     */
    public MultipleFramesAnimation createAnimation(final PImage... images)
    {
        checkNotNull(images, "images must not be null");
        return createAnimation(Arrays.asList(images));
    }

    /**
     * Create and return a new looped frames animation containing the specified frame images.
     *
     * @since 1.1
     * @param images one or more frame images, must not be null
     * @return a new looped frames animation containing the specified frame images
     */
    public LoopedFramesAnimation createLoopedAnimation(final PImage... images)
    {
        checkNotNull(images, "images must not be null");
        return createLoopedAnimation(Arrays.asList(images));
    }

    /**
     * Create and return a new list of frame images containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file or URL name, must not be null
     * @param suffix image suffix, must not be null
     * @param frames number of frames, must be at least one
     * @return a new list of frame images containing all the frame images
     *    specified from <code>baseImage</code>
     */
    public List<PImage> createFrameList(final String baseImage, final String suffix, final int frames)
    {
        checkNotNull(baseImage, "baseImage must not be null");
        checkNotNull(suffix, "suffix must not be null");
        if (frames < 1) {
            throw new IllegalArgumentException("frames must be at least 1");
        }
        int leadingZeros = (int) (frames / 10) + 1; // is this math correct?
        String format = "%s%0" + leadingZeros + "d%s";
        List<PImage> images = new ArrayList<PImage>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            PImage image = applet.loadImage(String.format(format, new Object[] { baseImage, frame, suffix }));
            images.add(image);
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * Create and return a new list of frame images containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image name, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new list of frame images containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public List<PImage> createFrameList(final String spriteSheet, final int x, final int y,
                                        final int width, final int height, final int frames)
    {
        checkNotNull(spriteSheet, "spriteSheet must not be null");
        return createFrameList(applet.loadImage(spriteSheet), x, y, width, height, frames);
    }

    /**
     * Create and return a new list of frame images containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image, must not be null
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new list of frame images containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public List<PImage> createFrameList(final PImage spriteSheet, final int x, final int y,
                                        final int width, final int height, final int frames)
    {
        checkNotNull(spriteSheet, "spriteSheet must not be null");
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
            PImage subimage = spriteSheet.get(x + (frame * width), y, width, height);
            images.add(subimage);
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * Create and return a sprite sheet image with the specified list of frame images.
     *
     * @param frameImages list of frame images, must not be null
     * @return a sprite sheet images with the specified list of frame images
     */
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
     * Create and return a sprite sheet image with the specified frame images.
     *
     * @since 1.1
     * @param frameImages one or more frame images, must not be null
     * @return a sprite sheet images with the specified list of frame images
     */
    public PImage createSpriteSheet(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return createSpriteSheet(Arrays.asList(frameImages));
    }

    /**
     * Create and return a new sprite sheet containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file or URL name, must not be null
     * @param suffix image suffix, must not be null
     * @param frames number of frames, must be at least one
     * @return a new sprite sheet containing all the frame images
     *    specified from <code>baseImage</code>
     */
    public PImage createSpriteSheet(final String baseImage, final String suffix, final int frames)
    {
        return createSpriteSheet(createFrameList(baseImage, suffix, frames));
    }

    /**
     * Flip the specified image horizontally.
     *
     * @param image image, must not be null
     * @return the specified image flipped horizontally
     */
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

    /**
     * Flip the specified frame images horizontally.
     *
     * @param frameImages list of frame images, must not be null
     * @return the specified frame images flipped horizontally
     */
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
     * Flip the specified frame images horizontally.
     *
     * @since 1.1
     * @param frameImages one or more frame images, must not be null
     * @return the specified frame images flipped horizontally
     */
    public List<PImage> flipHorizontally(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return flipHorizontally(Arrays.asList(frameImages));
    }

    /**
     * Flip the specified image vertically.
     *
     * @param image image, must not be null
     * @return the specified image flipped vertically
     */
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

    /**
     * Flip the specified frame images vertically.
     *
     * @param frameImages list of frame images, must not be null
     * @return the specified frame images flipped vertically
     */
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
     * Flip the specified frame images vertically.
     *
     * @since 1.1
     * @param frameImages one or more frame images, must not be null
     * @return the specified frame images flipped vertically
     */
    public List<PImage> flipVertically(final PImage... frameImages)
    {
        checkNotNull(frameImages, "frameImages must not be null");
        return flipVertically(Arrays.asList(frameImages));
    }

    /**
     * Rotate the specified image 360 degrees the specified number of steps.
     *
     * @param image image, must not be null
     * @param steps number of steps, must be at least one
     * @return the specified image rotated 360 degrees the specified number of steps
     */
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
