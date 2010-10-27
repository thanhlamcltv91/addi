## Copyright (C) 2006 David Bateman <dbateman@free.fr>
##
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; if not, write to the Free Software
## Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

## -*- texinfo -*-
## @deftypefn {Function File} {} tand (@var{x})
## Compute the tangent of an angle in degrees.  Returns zero for elements
## of for which @code{@var{x}/180} is an integer and @code{Inf} for elements
## where @code{(@var{x}-90}/180} is an integer.
## @seealso{tan, cosd, sind, acosd, asind, atand}
## @end deftypefn

function y = tand (x)
  if (nargin != 1)
    print_usage ();
  endif
  I0 = x / 180;
  I90 = (x-90) / 180;
  y = tan (I0 .* pi);
  y(I0 == round (I0) & finite (I0)) = 0;
  y(I90 == round (I90) & finite (I90)) = Inf;
endfunction;

//%!error(tand())
//%!error(tand(1,2))
//%!assert(tand(10:10:80),tan(pi*[10:10:80]/180),-10*eps)
//%!assert(tand([0,180,360]) == 0)
//%!assert(tand([90,270]) == Inf)

/*
@GROUP
trigonometric
@SYNTAX
angle = tand(value)
@DOC
Compute the tangent of an angle in degrees.
@EXAMPLES
<programlisting>
</programlisting>
@NOTES
@SEE
cos, acosh, sin, asin, asinh
*/
