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
## @deftypefn {Function File} {} hilb (@var{n})
## Return the Hilbert matrix of order @var{n}.  The
## @iftex
## @tex
## $i,\,j$
## @end tex
## @end iftex
## @ifinfo
## i, j
## @end ifinfo
## element of a Hilbert matrix is defined as
## @iftex
## @tex
## $$
## H (i, j) = {1 \over (i + j - 1)}
## $$
## @end tex
## @end iftex
## @ifinfo
##
## @example
## H (i, j) = 1 / (i + j - 1)
## @end example
## @end ifinfo
## @seealso{hankel, vander, sylvester_matrix, invhilb, toeplitz}
## @end deftypefn

## Author: jwe

function retval = hilb (n)


  if (nargin != 1)
    usage ("hilb (n)");
  endif

  nmax = length (n);
  if (nmax == 1)
    retval = zeros (n);
    tmp = 1:n;
    for x=1:n,
      retval (x, :) = 1.0 ./ (tmp + (x - 1));
    endfor
  else
    error ("hilb: expecting scalar argument, found something else");
  endif

endfunction

/*
@GROUP
specialmatrix
@SYNTAX
answer = template (value)
@DOC

@EXAMPLES
@NOTES
@SEE
rosser
*/
