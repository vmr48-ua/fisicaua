%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%                           %
%     Robotic Vision        %
%     Homework Set IV       %
%                           %
%     Víctor Mira Ramírez   %
%     800803577             %
%     vmirara@siue.edu      %
%                           %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Repeat the homogeneous camera calibration exercise of Sect. 11.2.1. 
% Investigate the effect of the number of calibration points, and noise
% on the calibration residual.

% This part is a repeat of MATLAB code in textbook, there is no easy
% replacement for Python, I would recommend you use MATLAB for this
% problem

P = mkcube(2);

% P = [[-1,-1,1,1,-1,-1];[-1,1,1,-1,-1,1,];[-1,-1,-1,-1,1,1]];

T_unknown = SE3(0.1, 0.2, 1.5) * SE3.rpy(0.1, 0.2, 0.3);
cam = CentralCamera('focal', 0.015, 'pixel', 10e-6, ...
    'resolution', [1280 1024], 'noise', 0.05); % gaussian noise

p = cam.project(P, 'objpose', T_unknown);
C = camcald(P, p);