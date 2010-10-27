## Copyright (C) 1995, 1996, 1997  Andreas Weingessel
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
## @deftypefn {Function File} {} blackman (@var{m})
## Return the filter coefficients of a Blackman window of length @var{m}.
##
## For a definition of the  Blackman window, see e.g. A. V. Oppenheim &
## R. W. Schafer, "Discrete-Time Signal Processing".
## @end deftypefn

## Author: AW <Andreas.Weingessel@ci.tuwien.ac.at>
## Description: Coefficients of the Blackman window

function c = blackman (m)

  if (nargin != 1)
    usage ("blackman (m)");
  endif

  if (! (isscalar (m) && (m == round (m)) && (m > 0)))
    error ("blackman: m has to be an integer > 0");
  endif

  if (m == 1)
    c = 1;
  else
    m = m - 1;
    k = (0 : m)' / m;
    c = 0.42 - 0.5 * cos (2 * pi * k) + 0.08 * cos (4 * pi * k);
  endif

endfunction

/*
@GROUP
signal
@SYNTAX
answer = blackman (value)
@DOC
.
@EXAMPLES
.
@NOTES
.
@SEE
bartlett, hamming, hanning
*/
