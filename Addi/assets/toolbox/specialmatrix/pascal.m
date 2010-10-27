## Copyright (C) 1999 Peter Ekberg
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
## @deftypefn {Function File} {} pascal (@var{n}, @var{t})
##
## Return the Pascal matrix of order @var{n} if @code{@var{t} = 0}.
## @var{t} defaults to 0. Return lower triangular Cholesky factor of 
## the Pascal matrix if @code{@var{t} = 1}. This matrix is its own
## inverse, that is @code{pascal (@var{n}, 1) ^ 2 == eye (@var{n})}.
## If @code{@var{t} = 2}, return a transposed and  permuted version of
## @code{pascal (@var{n}, 1)}, which is the cube-root of the identity
## matrix. That is @code{pascal (@var{n}, 2) ^ 3 == eye (@var{n})}.
##
## @seealso{hankel, vander, sylvester_matrix, hilb, invhilb, toeplitz
##           hadamard, wilkinson, compan, rosser}
## @end deftypefn

## Author: Peter Ekberg
##         (peda)

function retval = pascal (n, t)

  if (nargin > 2) || (nargin == 0)
    print_usage ();
  endif

  if (nargin == 1)
    t = 0;
  endif

  if (! isscalar (n) || ! isscalar (t))
    error ("pascal: expecting scalar arguments, found something else");
  endif

  retval = diag ((-1).^[0:n-1]);
  retval(:,1) = ones (n, 1);

  for jj = 2:n-1
    for ii = jj+1:n
      retval(ii,jj) = retval(ii-1,jj) - retval(ii-1,jj-1);
    endfor
  endfor

  if (t == 0)
    retval = retval*retval';
  elseif (t == 2)
    retval = retval';
    retval = retval(n:-1:1,:);
    retval(:,n) = -retval(:,n);
    retval(n,:) = -retval(n,:);
    if (rem(n,2) != 1)
      retval = -retval;
    endif
  endif

endfunction

/*
@GROUP
specialmatrix
@SYNTAX
pascal()
@DOC

@EXAMPLES
@NOTES
@SEE
rosser
*/