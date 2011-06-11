## -*- texinfo -*-
## @deftypefn {Function File} {} triplot (@var{tri}, @var{x}, @var{y})
## @deftypefnx {Function File} {} triplot (@var{tri}, @var{x}, @var{y}, @var{linespec})
## @deftypefnx {Function File} {@var{h} =} triplot (@dots{})
## Plot a triangular mesh in 2D.  The variable @var{tri} is the triangular
## meshing of the points @code{(@var{x}, @var{y})} which is returned from
## @code{delaunay}.  If given, the @var{linespec} determines the properties
## to use for the lines.  The output argument @var{h} is the graphic handle
## to the plot.
## @seealso{plot, trimesh, delaunay}
## @end deftypefn

function h = triplot (tri, x, y, varargin)

  if (nargin < 3)
    print_usage ();
  endif

  idx = tri(:, [1, 2, 3, 1]).';
  nt = size (tri, 1);
  if (nargout > 0)
    h = plot ([x(idx); NaN*ones(1, nt)](:),
	      [y(idx); NaN*ones(1, nt)](:), varargin{:});
  else
    plot ([x(idx); NaN*ones(1, nt)](:),
	  [y(idx); NaN*ones(1, nt)](:), varargin{:});
  endif
endfunction

%!demo
%! rand ('state', 2)
%! x = rand (20, 1);
%! y = rand (20, 1);
%! tri = delaunay (x, y);
%! triplot (tri, x, y);
