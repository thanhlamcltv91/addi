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
## @deftypefn {Function File} {} trace (@var{a})
## Compute the trace of @var{a}, @code{sum (diag (@var{a}))}.
## @end deftypefn

## Author: jwe

function y = trace (x)

  if (nargin != 1)
    usage ("trace (x)");
  endif

  [nr, nc] = size (x);
  if ((nr == 1) || (nc == 1))
    y = x(1);
  else
    y = sum (diag (x));
  endif

endfunction

/*
@GROUP
linearalgebra
@SYNTAX
trace(array)
@DOC
.
@EXAMPLES
.
@NOTES
.
@SEE
sum
*/

