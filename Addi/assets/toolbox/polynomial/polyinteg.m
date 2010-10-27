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
## @deftypefn {Function File} {} polyinteg (@var{c})
## Return the coefficients of the integral of the polynomial whose
## coefficients are represented by the vector @var{c}.
##
## The constant of integration is set to zero.
## @seealso{poly, polyderiv, polyreduce, roots, conv, deconv, residue,
## filter, polyval, and polyvalm}
## @end deftypefn

## Author: Tony Richardson <arichard@stark.cc.oh.us>
## Created: June 1994
## Adapted-By: jwe

function p = polyinteg (p)

  if(nargin != 1)
    print_usage ();
  endif

  if (! (isvector (p) || isempty (p)))
    error ("argument must be a vector");
  endif

  lp = length (p);

  if (lp == 0)
    p = [];
    return;
  end

  if (rows (p) > 1)
    ## Convert to column vector
    p = p.';
  endif

  p = [ p, 0 ] ./ [ lp:-1:1, 1 ];

endfunction

/*
@GROUP
polynomial
@SYNTAX
y = polyinteg (x)
@DOC
.
@NOTES
@EXAMPLES
@SEE
poly, polyreduce, polyval, roots, unmkpp
*/
