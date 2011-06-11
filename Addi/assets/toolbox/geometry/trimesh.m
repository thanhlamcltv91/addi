## -*- texinfo -*-
## @deftypefn {Function File} {} trimesh (@var{tri}, @var{x}, @var{y}, @var{z})
## @deftypefnx {Function File} {@var{h} =} trimesh (@dots{})
## Plot a triangular mesh in 3D.  The variable @var{tri} is the triangular
## meshing of the points @code{(@var{x}, @var{y})} which is returned 
## from @code{delaunay}.  The variable @var{z} is value at the point 
## @code{(@var{x}, @var{y})}.  The output argument @var{h} is the graphic 
## handle to the plot.
## @seealso{triplot, delaunay3}
## @end deftypefn

function h = trimesh (tri, x, y, z, varargin)

  if (nargin < 3)
    print_usage ();
  endif

  if (nargin == 3)
    triplot (tri, x, y);
  elseif (ischar (z))
    triplot (tri, x, y, z, varargin{:});
  else
    newplot ();
    if (nargout > 0)
      h = patch ("Vertices", [x(:), y(:), z(:)], "Faces", tri, 
		 "FaceColor", "none", "EdgeColor", __next_line_color__(), 
		 varargin{:});
    else
      patch ("Vertices", [x(:), y(:), z(:)], "Faces", tri, 
	     "FaceColor", "none", "EdgeColor", __next_line_color__(), 
	     varargin{:});
    endif

    if (! ishold ())
      set (gca(), "view", [-37.5, 30],
	   "xgrid", "on", "ygrid", "on", "zgrid", "on");
    endif
  endif
endfunction

%!demo
%! N = 10;
%! rand ('state', 10)
%! x = 3 - 6 * rand (N, N);
%! y = 3 - 6 * rand (N, N);
%! z = peaks (x, y);
%! tri = delaunay (x(:), y(:));
%! trimesh (tri, x(:), y(:), z(:));
