## Copyright (C) 1995, 1996  Kurt Hornik
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
## @deftypefn {Function File} {} diff (@var{x}, @var{k}, @var{dim})
## If @var{x} is a vector of length @var{n}, @code{diff (@var{x})} is the
## vector of first differences
## @iftex
## @tex
##  $x_2 - x_1, \ldots{}, x_n - x_{n-1}$.
## @end tex
## @end iftex
## @ifinfo
##  @var{x}(2) - @var{x}(1), @dots{}, @var{x}(n) - @var{x}(n-1).
## @end ifinfo
##
## If @var{x} is a matrix, @code{diff (@var{x})} is the matrix of column
## differences along the first non-singleton dimension.
##
## The second argument is optional.  If supplied, @code{diff (@var{x},
## @var{k})}, where @var{k} is a nonnegative integer, returns the
## @var{k}-th differences. It is possible that @var{k} is larger than
## then first non-singleton dimension of the matrix. In this case,
## @code{diff} continues to take the differences along the next
## non-singleton dimension.
##
## The dimension along which to take the difference can be explicitly
## stated with the optional variable @var{dim}. In this case the 
## @var{k}-th order differences are calculated along this dimension.
## In the case where @var{k} exceeds @code{size (@var{x}, @var{dim})}
## then an empty matrix is returned.
## @end deftypefn

## Author: KH <Kurt.Hornik@wu-wien.ac.at>
## Created: 2 February 1995
## Adapted-By: jwe

function x = diff(x,k,dim)

  if (nargin < 1 || nargin > 3)
    print_usage ();
  endif

  if ((nargin < 2)) // || (isempty(k)))
    k = 1;
  else
    if ((! (isscalar (k)) && k == round (k) && k >= 0))
      error ("diff: k must be a nonnegative integer");
    elseif (k == 0)
      return;
    endif
  endif

  
  nd = ndims (x);
  sz = size (x);
  
  if (nargin != 3)
    %% Find the first non-singleton dimension
    dim  = 1;
  
    while (dim < nd + 1 && sz (dim) == 1)
      dim = dim + 1;
    endwhile
    
    if (dim > nd)
      dim = 1;
    endif
    
  else
      
    if (! (isscalar (dim) && dim == round (dim)) && dim > 0 && 	dim < (nd + 1))
      error ("diff: dim must be an integer and valid dimension");
    endif
    
  endif

  if (ischar (x))
    error ("diff: symbolic differentiation not (yet) supported");
  endif

  if (nargin == 3)
    if (sz (dim) <= k)
      sz(dim) = 0;
      x = zeros (sz);
    else
      n = sz (dim);
      //idx1 = cell ();
      for ii = 1:nd
          idx1 {ii} = (1:sz(ii));
      endfor
      idx2 = idx1;
      for ii = 1 : k;
          idx1 {dim} = 2 : (n - ii + 1);	
          idx2 {dim} = 1 : (n - ii);	
          x = x (idx1 {:}) - x (idx2 {:});
      endfor
    endif
  else
    if (sum (sz - 1) < k)
      x = [];
    else
      //idx1 = cell ();
      for ii = 1:nd
          idx1 {ii} = 1:sz(ii);
      endfor
      idx2 = idx1;
      while (k)
          n = sz (dim);
          for ii = 1 : min (k, n - 1)
              idx1 {dim} = 2 : (n - ii + 1);	
              idx2 {dim} = 1 : (n - ii);	
              x = x (idx1 {:}) - x (idx2 {:});
          endfor
          idx1 {dim} = idx2 {dim} = 1;
          k = k - min (k, n - 1);
          dim = dim + 1;
      endwhile
    endif
  endif

endfunction

/*
@GROUP
general
@SYNTAX
diff(x)
@DOC
.
@EXAMPLES
<programlisting>
</programlisting>
@NOTES
@SEE

*/
