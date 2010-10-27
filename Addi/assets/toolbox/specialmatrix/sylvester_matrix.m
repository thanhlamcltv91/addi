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
## @deftypefn {Function File} {} sylvester_matrix (@var{k})
## Return the Sylvester matrix of order
## @iftex
## @tex
## $n = 2^k$.
## @end tex
## @end iftex
## @ifinfo
## n = 2^k.
## @end ifinfo
## @seealso{hankel, vander, hilb, invhilb, toeplitz}
## @end deftypefn

## Author: jwe

function retval = sylvester_matrix (k)

  if (nargin != 1)
    print_usage ();
  endif

  if (isscalar (k))
    if (k < 1)
      retval = 1;
    else
      tmp = sylvester_matrix (k-1);
      retval = [tmp, tmp; tmp, -tmp];
    endif
  else
    error ("sylvester_matrix: expecting scalar argument");
  endif

endfunction

/*
@GROUP
specialmatrix
@SYNTAX
retval = sylvester_matrix (k)
@DOC
.
@EXAMPLES
@NOTES
@SEE
*/
