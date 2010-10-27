## Copyright (C) 1995, 1996, 1997  Kurt Hornik
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
## @deftypefn {Function File} {} var (@var{x})
## For vector arguments, return the (real) variance of the values.
## For matrix arguments, return a row vector contaning the variance for
## each column.
##
## The argument @var{opt} determines the type of normalization to use.
## Valid values are
##
## @table @asis 
## @item 0:
## Normalizes with N-1, provides the best unbiased estimator of the
## variance [default].
## @item 1:
## Normalizes with N, this provides the second moment around the mean.
## @end table
##
## The third argument @var{dim} determines the dimension along which the 
## variance is calculated.
## @end deftypefn

## Author: KH <Kurt.Hornik@wu-wien.ac.at>
## Description: Compute variance

function y = var (x, opt, dim)

  if (nargin < 1 || nargin > 3)
    print_usage ();
  endif
  
  if (nargin < 3)
    dim = find (size (x) > 1, 1);
    if (isempty (dim))
      dim = 1;
    endif
  endif
  
  if (nargin < 2 ) //|| isempty (opt))
    opt = 0;
  endif

  sz = size (x);
  
  if (prod (sz) < 1)
    error ("var: x must not be empty");
  elseif (sz(dim) == 1)
    y = 0;
  else
    rng = ones (1, length (sz));
    rng (dim) = sz (dim);
    
    if (opt == 0)
      y = sumsq (x - repmat(mean (x, dim), rng), dim) / (sz(dim) - 1);
    else
      y = sumsq (x - repmat(mean (x, dim), rng), dim) / sz(dim);
    endif

  endif

endfunction

/*
@GROUP
statistics
@SYNTAX
var([2,3,8,...])
@DOC
Calculates the variance.
@EXAMPLES
<programlisting>
var([3,4,5]) -> 1
</programlisting>
@SEE
mean, std
*/
