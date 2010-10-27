## Copyright (C) 1996, 1997 John W. Eaton
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
## @deftypefn {Function File} {} poly (@var{a})
## If @var{a} is a square @math{N}-by-@math{N} matrix, @code{poly (@var{a})}
## is the row vector of the coefficients of @code{det (z * eye (N) - a)},
## the characteristic polynomial of @var{a}.  If @var{x} is a vector,
## @code{poly (@var{x})} is a vector of coefficients of the polynomial
## whose roots are the elements of @var{x}.
## @end deftypefn

## Author: KH <Kurt.Hornik@wu-wien.ac.at>
## Created: 24 December 1993
## Adapted-By: jwe

function y = poly (x)

  if (nargin != 1)
    print_usage ();
  endif

  m = min (size (x));
  n = max (size (x));
  if (m == 0)
    y = 1;
    return;
  elseif (m == 1)
    v = x;
  elseif (m == n)
    v = eig (x);
  else
    print_usage ();
  endif

  y = zeros (1, n+1);
  y(1) = 1;
  for jj = 1:n;
    y(2:(jj+1)) = y(2:(jj+1)) - v(jj) .* y(1:jj);
  endfor

  if (all (all (imag (x) == 0)))
    y = real (y);
  endif

endfunction

/*
@GROUP
polynomial
@SYNTAX
y = poly (x)
@DOC
.
@NOTES
@EXAMPLES
@SEE
mkpp, polyreduce, polyval, roots, unmkpp
*/

