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
## @deftypefn {Function File} {} hankel (@var{c}, @var{r})
## Return the Hankel matrix constructed given the first column @var{c}, and
## (optionally) the last row @var{r}.  If the last element of @var{c} is
## not the same as the first element of @var{r}, the last element of
## @var{c} is used.  If the second argument is omitted, it is assumed to
## be a vector of zeros with the same size as @var{c}.
##
## A Hankel matrix formed from an m-vector @var{c}, and an n-vector
## @var{r}, has the elements
## @iftex
## @tex
## $$
## H (i, j) = \cases{c_{i+j-1},&$i+j-1\le m$;\cr r_{i+j-m},&otherwise.\cr}
## $$
## @end tex
## @end iftex
## @ifinfo
##
## @example
## @group
## H(i,j) = c(i+j-1),  i+j-1 <= m;
## H(i,j) = r(i+j-m),  otherwise
## @end group
## @end example
## @end ifinfo
## @seealso{vander, sylvester_matrix, hilb, invhilb, toeplitz}
## @end deftypefn

## Author: jwe

function retval = hankel (c, r)

  if (nargin == 1)
    r = zeros(size(c)); //resize (resize (c, 0), size(c));
  elseif (nargin != 2)
    print_usage ();
  endif

  [c_nr, c_nc] = size (c);
  [r_nr, r_nc] = size (r);

  if ((c_nr != 1 && c_nc != 1) || (r_nr != 1 && r_nc != 1))
    error ("hankel: expecting vector arguments");
  endif

  if (nargin == 1)
    r (1) = c (length (c));
  endif

  if (c_nc != 1)
    c = c.';
  endif

  if (r_nr != 1)
    r = r.';
  endif

  nc = length (r);
  nr = length (c);

  if (r (1) != c (nr))
    warning ("hankel: column wins anti-diagonal conflict");
  endif

  ## This should probably be done with the colon operator...

  retval = zeros(nr,nc), //resize (resize (c, 0), nr, nc);

  for ii = 1:min (nr, nc)
    retval (1:nr-ii+1, ii) = c (ii:nr);
  endfor

  tmp = 1;
  if (nc <= nr)
    tmp = nr - nc + 2;
  endif

  for ii = nr:-1:tmp
    retval (ii, 2+nr-ii:nc) = r (2:nc-nr+ii);
  endfor

endfunction

//%!assert(hankel(1:3),[1,2,3;2,3,0;3,0,0])
//%!assert(hankel(1),[1]);
//%!assert(hankel(1:3,3:6),[1,2,3,4;2,3,4,5;3,4,5,6]);
//%!assert(hankel(1:3,3:4),[1,2;2,3;3,4]);
//%!assert(hankel(1:3,4:6),[1,2,3;2,3,5;3,5,6]);

/*
@GROUP
specialmatrix
@SYNTAX
hankel()
@DOC

@EXAMPLES
@NOTES
@SEE
rosser
*/
