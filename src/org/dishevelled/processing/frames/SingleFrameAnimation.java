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

import processing.core.PImage;

/**
 * Single frame animation.
 *
 * @author  Michael Heuer
 */
public final class SingleFrameAnimation implements Animation
{
    private final PImage currentFrame;

    SingleFrameAnimation(final PImage currentFrame)
    {        
        if (currentFrame == null)
        {
            throw new NullPointerException("currentFrame must not be null");
        }
        this.currentFrame = currentFrame;
    }


    @Override
    public boolean advance()
    {
        return false;
    }

    @Override
    public PImage getCurrentFrame()
    {
        return currentFrame;
    }
}