## Copyright (C) 1995, 1996, 1997  Friedrich Leisch
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
## @deftypefn {Function File} {} rectangle_sw (@var{n}, @var{b})
## Rectangular spectral window.  Subfunction used for spectral density
## estimation.
## @end deftypefn

## Author: FL <Friedrich.Leisch@ci.tuwien.ac.at>
## Description: Rectangular spectral window

function retval = rectangle_sw (n, b)

  retval = zeros (n, 1);
  retval(1) = 2 / b + 1;

  l = (2:n)' - 1;
  l = 2 * pi * l / n;

  retval(2:n) = sin ((2/b + 1) * l / 2) ./ sin (l / 2);

endfunction

/*
@GROUP
signal
@SYNTAX
rectangle_sw
@DOC
.
@EXAMPLES
.
@NOTES
Rectangular spectral window.  Subfunction used for spectral density estimation.
@SEE
rectangle_lw
*/




























