## Copyright (C) 1999,2000  Kai Habel
##
## This file is part of Octave.
##
## Octave is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2, or (at your option)
## any later version.
##
## Octave is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with Octave; see the file COPYING.  If not, write to the Free
## Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
## 02110-1301, USA.

## -*- texinfo -*-
## @deftypefn {Function File} {} copper (@var{n})
## Create color colormap. This colormap is black to a light copper tone.
## The argument @var{n} should be a scalar.  If it
## is omitted, the length of the current colormap or 64 is assumed.
## @seealso{colormap}
## @end deftypefn

## Author:  Kai Habel <kai.habel@gmx.de>

function map = copper (number)

  if (nargin == 0)
    number = rows (colormap);
  elseif (nargin == 1)
    if (! isscalar (number))
      error ("copper: argument must be a scalar");
    endif
  else
    print_usage ();
  endif

  if (number == 1)
    map = [0, 0, 0];  
  elseif (number > 1)
    x = linspace (0, 1, number)';
    r = (x < 4/5) .* (5/4 * x) + (x >= 4/5);
    g = 4/5 * x;
    b = 1/2 * x;
    map = [r, g, b];
  else
    map = [];
  endif

endfunction

/*
@GROUP
image
@SYNTAX
copper
@DOC
Create color colormap. This colormap is black to a light copper tone.
@EXAMPLES
<programlisting>
.
</programlisting>
@SEE
autumn, bone, cool, hot, jet, pink, rainbow, spring, summer, white, winter
*/
