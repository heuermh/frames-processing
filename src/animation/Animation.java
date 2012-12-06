/*

    Animation library for Processing.
    Copyright (c) 2012 held jointly by the individual authors.

    This file is part of Animation library for Processing.

    Animation library for Processing is free software: you can redistribute it and/or
    modify it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Animation library for Processing is distributed in the hope that it will be
    useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Animation library for Processing.  If not, see
    <http://www.gnu.org/licenses/>.

*/
package animation;

import processing.core.PImage;

/**
 * One or more frames in an animation.
 *
 * @author  Michael Heuer
 */
public interface Animation
{
    boolean advance();
    PImage getCurrentFrame();
}