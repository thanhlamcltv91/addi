## -*- texinfo -*-
## @deftypefn {Function File} {[@var{C}, @var{F}] =} voronoin (@var{pts})
## @deftypefnx {Function File} {[@var{C}, @var{F}] =} voronoin (@var{pts}, @var{options})
## computes n- dimensional voronoi facets.  The input matrix @var{pts}
## of size [n, dim] contains n points of dimension dim.
## @var{C} contains the points of the voronoi facets.  The list @var{F}
## contains for each facet the indices of the voronoi points.
##
## A second optional argument, which must be a string, contains extra options
## passed to the underlying qhull command.  See the documentation for the
## Qhull library for details.
## @seealso{voronoin, delaunay, convhull}
## @end deftypefn

## Author: Kai Habel <kai.habel@gmx.de>
## First Release: 20/08/2000

## 2003-12-14 Rafael Laboissiere <rafael@laboissiere.net>
## Added optional second argument to pass options to the underlying
## qhull command

function [C, F] = voronoin (pts, opt)

  if (nargin != 1 && nargin != 2)
    print_usage ();
  endif

  [np, dims] = size (pts);
  if (np > dims)
    if (nargin == 1)
      [C, F, infi] = __voronoi__ (pts);
    elseif ischar(opt)
      [C, F, infi] = __voronoi__ (pts, opt);
    else
      error ("voronoin: second argument must be a string");
    endif

  else
    error ("voronoin: number of points must be greater than their dimension");
  endif
endfunction
