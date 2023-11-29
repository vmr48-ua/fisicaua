# J. Fernandez-Rossier
# This code was developped in July-November 2018
# Solution of Schrodinger equation in 1D

# Bound states
#  Discretize 1D equation in a line
#  Set potential in the middle of the line
#  Diagonalize sparse matrix for a few eigenstates

# Scattering states
#  Numerical propagation of wave function from one side of the barrier
#  to the other; algebraic determination of scattering coefficient t_k

# Minor editions in 2021 edition

import numpy as np
import scipy.linalg as spy
import matplotlib.pyplot as plt

figurenum=1

            ##########################################################
            #   TABLE OF CONTENTS                                    #
            #                                                        #
            #   I. MAIN FUNCTIONS                                    #
            #   II. Plot functions                                   #
            #   III.  Potential functions                            #
            #   IV.   Auxilary functions                             #
            #   V.  Solution functions (check in case of despair).   #
            ##########################################################

#################################################################################
#################################################################################
"""
 I.  MAIN  FUNCTIONS

  1) HAM  :  computes hamiltonian matrix for a given potential
  2) TRANS:  computes the transmission coefficient T(E) for a given potential
"""

def ham(xmax,N,potfun,param,mass):
    '''
    Description
    discretize kinetic operator -h^2/2m (d^2/dx^2) in the line (-xmax, xmax) 
    INPUTS
    *xmax is a real number. It goes in AA
    *N  (integer) is the number of points in the grid.
    Therefore, there are N-1 segments
    * potfun is a function f(x,param), to be provided externally
    * param is a set of parameters, 
    
    -hbar^2/2m Phi``= (hbar^2/2m delta^2)  ( + 2 Phi(n) - Phi(n+1)-Phi(n+1))
    *ADDS potential in diagonal

    OUTPUT: A dimension N numpy array (matrix) with the discretized Hamiltonian
    in meV
    '''
    from scipy.sparse import dia_matrix
    
    dx=2.*xmax/float(N-1) #size of step
    
    hbarsquareovertwom=calchbarsquareovertwom() # approximately in meV AA^2
    
    epsilon=hbarsquareovertwom/(dx*dx)  # energy in meV
    epsilon=epsilon/mass # if mass=1,  we have electrons
    
    mat=np.array([[0.0j for i in range(N)] for j in range(N)])
    #dia_matrix((N,N),dtype=np.float).toarray()  # this creates an empty matrix
     
    #  We now fill the diagonal
    x=-xmax
    for i in np.arange(N):
        mat[i,i]=+2.*epsilon+potfun(x,param)  #
        x=x+dx

    #  We now fill the positive co-diagonal the (i,i+1) terms
    for i in np.arange(N-1):
        mat[i,i+1]=-epsilon

    #  We now fill the diagonal the (i,i+1) terms
    for i in np.arange(N-1):
        mat[i+1,i]=mat[i,i+1]
    
    return mat # in meV

def trans(xmax,N,potfun,param,mass,ener):
    ''' COMPUTES TRANSMISSION COEFFICIENT for quantum particle with
    energy E and m=mass,  in 1D, with potential given by potfun(x,param).


    INPUTS:
    xmax:  simulation cell (-xmax,xmax)
    N: number of grid points for simulation cell
    
    Method:  
    1) For E and m, k is computed
    2) Wave function is assumed to be Psi= e^{i kx} for x=xmax+dx, and xmax+dx+dx
    3) Wave function is propagated backwards, with the discretized Schrodinger
    4) Wave function is assumed to be t^{-1} e^{ikx} + (r/t) e^{-ikx} at xmin-dx
    5) t and (r/t), complex numbers,  are determined solving a linear equation
    6) T=|t(E)|^2 is returned
    '''
    
    hbarsquareovertwom=calchbarsquareovertwom() # approximately in meV AA^2
    
    dx=2*xmax/float(N-1) #size of step

    #  NOTICE that xmin=0 !!!! 

    k=np.sqrt(mass*ener/hbarsquareovertwom) # k is in AA-1

      
    epsilon=hbarsquareovertwom/(dx*dx*mass)
    
    wave=np.array([0.0j for i in range(N)])
    potlist=np.array([0. for i in range(N)])
    xlist=np.array([0. for i in range(N)])
    
    x=xmax
    wave[N-1]=phase(k*x)
    wave[N-2]=phase(k*(x-dx))
    wave[N-3]=phase(k*(x-2*dx))
    #    print(wave[N-1])
    #    print 'k*xmazmax=',k*xmax
    #    print 'k=',k
    
    potlist[N-1]=potfun(x,param)
    potlist[N-2]=potfun(x-dx,param)
    potlist[N-3]=potfun(x-2*dx,param)

    xlist[N-1]=x
    xlist[N-2]=x-dx
    xlist[N-3]=x-2*dx

    
    x=xmax-3*dx

                   
    for i in np.arange(3,N+1):
        pot=potfun(x,param)
        pref=(pot-ener)/epsilon
        wave[N-i]=(pref+2.)*wave[N-i+1]-wave[N-i+2]
        potlist[N-i]=pot
        xlist[N-i]=x
        x=x-dx
    # Determination of t_k 
    #  (t_k^-1) exp(i k x) + rk/tk exp (-ikx) =wave(x)
    #  (t_k^-1) exp(i k x) - rk/tk exp (-ikx) =(d wave(x)/dx)/ik  =wave'/ik
    #  2* (t_k^-1) exp(i k x)= wave(x)+ (1/ik)    wave'
    # tk= 2 exp(i k x)/(wave(x)+ (1/ik)    wave'))
    #   tk = 2 i k e^(ikx)/(wave'+ i k wave)

    #   We determine the derivative 
    #    print wave[[2]]
    wavederiv0=(wave[2]-wave[1])/dx
    x=xlist[1]
    tk=2.*1j*k*phase(k*x)/(1j*k*wave[1]+wavederiv0)
                           
    T=np.absolute(tk)
    
    return T*T

#################################################################################
#################################################################################
"""
 II.  PLOT FUNCTIONS

  1)  plotstates  (to plot bound states, wave functions and their potential
  2)  plotT:  plots T(E)
  3)  Plotpot: to plot the potential only.
"""  

def plotstates(mat,kval,xmax,N,potfun,param):
    '''
    INPUTS:
    * mat is a  hamiltonian matrix, obtained with HAM.
    * kval is the number of states that are going to be plotted
    * xmax and N define the grid (same as in HAM to define mat)
    * potfun: pofun(x,param) is a used-defined function that defines the potential

    OUTPUT
    a figure showing V(x), the energy levels and the wave functions.
    '''

    global figurenum

    dx=2.*xmax/float(N-1)
    #    xlist=np.arange(-xmax,xmax+dx,dx)
    potlist=[]
    xlist=[]
    x=-xmax
    for k in range(N):
        xlist.append(x)
        potlist.append(potfun(x,param))
        x=x+dx

    ymin=np.min(potlist)
    #   ymin=-200

    plt.plot(xlist,potlist)
    ener,wave=spy.eigh(mat)
    
    enerlist=[]
    wavelist=[]
    colorlist=['mediumblue','red','green','brown','orange','blueviolet']
    colorlist0=['b','r','g','c','m','y','k']
    colorlist=colorlist+colorlist0+colorlist
    
    for i in range(kval):
        wavelist.append(wave[:,i])
        y=ener[i]+500*wavelist[i]
        plt.plot(xlist,y,color=colorlist[i])
        plt.fill_between(xlist,ener[i],y,facecolor=colorlist[i])

        subener=[]
        for x in xlist:
            subener.append(ener[i])
        
        print(ener[i])

        enerlist.append(subener)  # flat line with height ener
        plt.plot(xlist,enerlist[i],color='black')
    
    
       
    
    plt.xlabel('x')
    plt.ylabel('Energy (meV)')
    plt.ylim(potfun(-xmax,k),potfun(xmax,k))
    plt.xlim(-xmax,xmax)
    plt.draw()
    plt.show()

    return enerlist

def plotT(xmax,N,mass,emin,emax,dE,potfun,param):
    '''
    This function plots T(E), for emin<E<emax, for
    a potential defined by potfun(x,param).
    mass= mass in units of electrom mass.
    
    '''
    Elist=np.arange(emin,emax,dE)
    Tlist=np.array([0.0j for i in range(len(Elist))])
    TSQ=np.array([0.0j for i in range(len(Elist))])

    for i in range(len(Elist)):
        ee=Elist[i]
        TT=trans(xmax,N,potfun,param,mass,ee)
        Tlist[i]=TT
    #     transquare(ener,d,m,V0)                                            
    
    plt.plot(Elist,Tlist)
    #    plt.plot(Elist,TSQ)
    
    #    plt.plot(xlist,wave.imag,'o')
    #    plt.plot(xlist,potlist)
    #    print "xmin=",x
    #    print xlist
       
    plt.ylim([0,1])
    plt.xlabel('E(meV)')
    plt.ylabel('Transmission')
    plt.draw()
    plt.show()                 
    
    return

def plotpot(xmax,N,potfun,param):
    ''' 
    This function plots a potential in given by potfun(x,param), where
    param can be one or many parameters  in the grid defined by xmax and N

    Example
    plotpot(40,100,doublebarrier,(-20,-10,10,20,100,100))
    plots the double barrier potential.
    '''
           
    potlist=np.array([0. for i in range(N)])
    xlist=np.array([0. for i in range(N)])
    
    
    
    dx=2*xmax/float(N-1)
    x=-xmax

                   
    for i in np.arange(N):
        pot=potfun(x,param)
        potlist[i]=pot
        xlist[i]=x
        x=x+dx

    plt.plot(xlist,potlist)
    #    plt.plot(xlist,wave.imag,'o')
    #    plt.plot(xlist,potlist)
    #    print "xmin=",x
    #    print xlist

        
       
    
    plt.xlabel('x(AA)')
    plt.ylabel('Potential (meV)')
    plt.draw()
    plt.show()                 
    
    return

#################################################################################
#################################################################################
"""
 III.  Potenials
  1.  harmonic: Harmonic potential
  2.  Well:  square well/barrier
  3.  Double barrier
  4.  Multiple barrier
"""

def harmonic(x,k):
    '''
    INPUtS:  k in meV**AA^-2,  x in AA
    OUTPUT: harmonic potential in meV'''

    return (1/2)*k*x*x

def well(x,param):
    '''
    param=(x0,L,V0):

    this function returns V0 if x0<x<x0+L
    '''
    x0=param[0]
    L=param[1]
    V0=param[2]
    if(x>x0 and x<(x0+L)):
        pot=V0
    else:
        pot=0

    return pot

def doublebarrier(x,param):
    #def doublebarrier(x,x0,x1,x2,x3, V1,V2):

    ''' This function returns a piecewise function of x, that takes the following values:
       if x<x0    0
       if x0<x<x1:  V1
       if x1<x<x2:  0
       if x2<x<x3:  V2
       if x>x3  0
    '''
    
    x0=param[0]
    x1=param[1]
    x2=param[2]
    x3=param[3]
    V1=param[4]
    V2=param[5]
    pot=0.
    
    if (x<x0):
        pot=0.
        
    if(x0<x<x1):
        pot=V1
        
    if(x1<x<x2):
        pot=0.
        
    if(x2<x<x3):
        pot=V2
      
    return pot

def multbarrier(x,param):
    ''' This function returns a piecewise function of x, that generats 
    N barrierrs pot=V, of width w1,  separated by (N-1) regions of width w2 and V=0takes the following values:
      
    '''
    
    pot=0.
    N=param[0]
    x0=param[1]
    w1=param[2]
    w2=param[3]
    V=param[4]
    
    if (x<x0):
        pot=0.
   
    for i in range(N):
        xmin=x0+i*(w1+w2)
        xmax=xmin+w1
        if(xmin<x<xmax):
            pot=V
        if(xmax<x<xmax+w2):
            pot=0.
            
    return pot

def ssh(x,param):
    ''' [cos(x/L)+cos(2x/L)]*V0
    '''

    L=param[0]
    V0=param[1]
    pot=V0*(np.cos(x/L)+np.cos(2*x/L))
    return pot

#################################################################################
#################################################################################
"""
 IV AUXILIARY FUNCTIONS

  1.  calchbarsquareovertwom
  2.  phase
  3.  transquare
  4.  getener (provides eigenvalues for an input matrix)
  5.   ladder operator 
"""

def calchbarsquareovertwom():
    ''' This function computes hbar^2/2 m, where m is the electron mass
    and returns its value in meV AA*2
    '''
    
    hbar=1.054e-34 #J.s
    #    hbar=6.5821191e-15 # eV*s
    mass=9.10938356e-31 # Kg
    
    c=0.5*hbar*hbar/mass # this goes in Joule*Joule*s*s/Kg= Joule m^2
    #   Joule= kg*meter^2* s^-2
    c=c*1e20/1.602e-19 # this goes in eV AA^2
    

    
    return c*1e3 # meV AA*2

def phase(alpha):
    return np.cos(alpha)+1j*np.sin(alpha)

def transquare(ener,d,mass,V0):
    ''' 
    Computes T(E) for square barrier using analytical formula
    '''
    
    hbarsquareovertwom=calchbarsquareovertwom()

    # https://en.wikipedia.org/wiki/Rectangular_potential_barrier

    #    x=paramsquarebarrier(d,mass,V0)
    #   x2=x*x
    
    if(np.abs(ener-V0)<0.0001):
        a=4.*V0*d*d*mass/hbarsquareovertwom
        T=1./(1.+0.25*a)
        return T
        

    if(ener<V0):
        k1=np.sqrt(mass*(V0-ener)/hbarsquareovertwom) 
        a=V0*V0*(np.sinh(k1*d))**2.
        b=4.*ener*(V0-ener)
        T=1./(1.+(a/b))
    else:
        k1=np.sqrt(mass*(ener-V0)/hbarsquareovertwom) 
        a=V0*V0*(np.sin(k1*d))**2.
        b=4.*ener*(ener-V0)
        T=1./(1.+(a/b))
    
    return T

def getener(mat,kval):
    ''' returns the first kval eigenvalues of kmat
    '''
    ener=spy.eigvalsh(mat)
    ener0=[]
    for i in range(kval):
        ener0.append(ener[i])
    return ener0

def creation(vec,xmax,N):

    dx=2.*xmax/(N-1)
    
    size=len(vec)
    vec2=[]
    vec2.append(0)

    x=-xmax
    for i in np.arange(1,N):
        deriv=(vec[i]-vec[i-1])/dx
        vec2.append(-deriv+x*vec[i])

        x=x+dx
        
        
        

    return vec2

def destroy(vec,xmax,N):

    dx=2.*xmax/(N-1)
    
    size=len(vec)
    vec2=[]
    vec2.append(0)

    x=-xmax
    for i in np.arange(1,N):
        deriv=(vec[i]-vec[i-1])/dx
        vec2.append(deriv+x*vec[i])

        x=x+dx
        
        
        

    return vec2
    
#################################################################################
#################################################################################
"""
 V SOLUTION FUNCTIONS
  1. comparesquare (calculates T(E) using analytical formula and trans.
  2. compHW (compares HW potential, analytically and numerically
  3. HW. computes energy levels or hard wall, using analytical formulas
  4. waveHW same than 3. but for the wave function.
  5. comphar  :compares energy levels for harmonic oscillator (numeric and analytical)
  6. hbarw:  computes hbar*w for a given k and m
"""

def comparesquare(emin,emax,dE,mass,d,V0,N):
    '''
    This function plots T(E), for emin<E<emax, for
    a potential defined by potfun(x,param).
    mass= mass in units of electrom mass.
    '''

    Elist=np.arange(emin,emax,dE)
    Tlist=np.array([0.0j for i in range(len(Elist))])
    TSQ=np.array([0.0j for i in range(len(Elist))])
    param=[]
    
    param.append(0) # x0
    param.append(d) #barrier widh
    param.append(V0)
    xmax=d+5.

    for i in range(len(Elist)):
        ee=Elist[i]
        TT=trans(xmax,N,well,param,mass,ee)
        Tlist[i]=TT
        TSQ[i]=transquare(ee,d,mass,V0)                                            
    
    plt.plot(Elist,Tlist)
    plt.plot(Elist,TSQ)
    
    #    plt.plot(xlist,wave.imag,'o')
    #    plt.plot(xlist,potlist)
    #    print "xmin=",x
    #    print xlist

    plt.ylim([0,1])
    plt.xlabel('E(meV)')
    plt.ylabel('Transmission')
    plt.draw()
    plt.show()                 
    
    return

def compHW(xmax,N,mass,nlevels):
    '''This function compares the energy levels for the Hard Wall potential
        computed numerically, and analytically.
        '''
    L=2*xmax #this overlooks that in the numerics the wave function
    #           does not vanish in the boundaries
    #    L=L+2*(xmax/(N-1)) # this corrects for that
    mat=ham(xmax,N,harmonic,0,mass)
    enum=getener(mat,nlevels)

    S=0
    
    for n in range(1,nlevels+1):
        Ean=HW(L,mass,n)
        dif=Ean-enum[n-1]
        print( 'analytical, numerical, dif',Ean,enum[n-1],dif)
        S=S+dif*dif

    for n in range(1,nlevels+1):
        Ean=HW(L,mass,n)
        dif=Ean-enum[n-1]
        rel=dif/Ean
        print('n,relative error',n, rel )



    return S

def HW(L,mass,n):
    ''' Computes energy levels for harwall potential
    '''
    hbarsquareovertwom=calchbarsquareovertwom()
    ener=hbarsquareovertwom*(np.pi*n)**2.
    ener=ener/mass
    ener=ener/(L*L)
    
    return ener #mev

def waveHW(x,param):
    n=param[0]
    L=param[1]
    xp=x-L*0.5
    norm=np.sqrt(2./L)
    wave=norm*np.sin(np.pi*xp*n/L)
    return wave

###########################
###########################
def comphar(xmax,N,mass,k,nlevels):
    '''
    This function compares the energy levels for the harmonic potential
    computed numerically, and analytically.
    '''

    L=2*xmax
    mat=ham(xmax,N,harmonic,k,mass)
    enum=getener(mat,nlevels)

    hw=hbarw(k,mass)
    S=0
    
    for n in range(nlevels):
        Ean=hw*(n+0.5)
        dif=Ean-enum[n]
        print('analytical, numerical, dif',Ean,enum[n],dif)
        S=S+dif*dif

    return S

def hbarw(k,m):
    ''' 
    k goes in eV/AA^2 (electron volts times inverse Amnstrog square)
    m goes in electron mass
    
    (hbar omega^2)= hbar?2 k/m
    
    '''
    
    hbarsquareovertwom=3826.27

    hbarsquareovertwom=calchbarsquareovertwom()
    
    hbarw2=2.*hbarsquareovertwom*k/m
    
    return np.sqrt(hbarw2)


####################
#   Resonant tunneling vs barrier distance
#  Version of 2020.   Choose d spacing = grid
#

def  restun(dmin,dmax,ener,W,V0,mass):
    ''' 
    This function plots T(E,d) for a double barrier with width W, height V0,
    for energies in  Eparam,   as a function of  barrier distance
    '''
    
    xmax=dmax+W+20.
    N=2000
    dd=2*xmax/(N-1)
    x0=-xmax+10*dd
    Tlist=[]
    dlist=np.arange(dmin,dmax,dd)
    
    for d in dlist:
        
        x1=x0+W
        x2=x1+d
        x3=x2+W
        V1=V0
        V2=V0
        T=trans(xmax,N,doublebarrier,(x0,x1,x2,x3,V1,V2),mass,ener)
        Tlist.append(T)

    plt.plot(dlist,Tlist)
    plt.draw()
    plt.show()
    

    return

#################  November 17 2018
def compdouble(R,T,alpha):

    T2=T*T
    T2=T2/(T2+4*R*np.sin(alpha)**2.)
    return T2

################ November 6 2019
def deltaener(V0,a,mass):
    ''' this function calculates bound state energy for Dirac bound state 1D
        V0 is in meV
        a is in AA
        mass=1 is electrons mass

        E=-m(a V0)^2/2 hbar^2= (a V0)^2/ (hbar^2/2m)
        
    '''
    num=mass*(V0*a)**2.
    den=4*calchbarsquareovertwom()
    return -num/den

######################  November  8 2019
def xcl(ener,k,xmax,N):
    ''' given an energy, and the spring constant,  the code determines
    the classical return point and the closer $n$ point in the grid
    '''

    x=np.sqrt(2.*ener/k)
    dx=2*xmax/(N-1)
    n=int((x+xmax)/dx)
    return n,x

def forbid(kval,k,mass,xmax,N):
    ''' ths function computes the probability of finding the electron in the
    forbidden zone for the first kval values, for the harmonic oscillator
    '''

    potfun=harmonic
    param=k
    print('computing matrix')
    mat=ham(xmax,N,potfun,param,mass)
    print('diagonalazing matrix')
    E,wave=spy.eigh(mat)
    

    for nn in np.arange(kval):
        nin,x= xcl(E[nn],k,xmax,N)
        vec=wave[:,nn]
        prob=inte(xmax,N,vec,nin)
        print("n,prob,x,nin",nn,2*prob,x,nin)

    for nn in np.arange(1,kval):
        print(nn,E[nn]-E[nn-1])
        
    return

def inte(xmax,N,vec,n1):
    ''' Integrates vec^2 fron n1 to N
    '''

    dx=2*xmax/(N-1)
    area=0.
    for k in np.arange(n1,N):
        wave2=vec[k]*np.conjugate(vec[k])
        area=area+dx*wave2

    return abs(area)
        

####################### EDICIONES POSTERIORES AL UPLOAD del 3-N-2020###################
def plotonestate(mat,kval,xmax,N,potfun,param):
    '''
    INPUTS:
    * mat is a  hamiltonian matrix, obtained with HAM.
    * kval is the number of states that are going to be plotted
    * xmax and N define the grid (same as in HAM to define mat)
    * potfun: pofun(x,param) is a used-defined function that defines the potential

    OUTPUT
    a figure showing V(x), and staet kval

    IDENTICAL to plotstates, but only plots 1    
    '''

    global figurenum

    dx=2.*xmax/float(N-1)
    #    xlist=np.arange(-xmax,xmax+dx,dx)
    potlist=[]
    xlist=[]
    x=-xmax
    for k in range(N):
        xlist.append(x)
        potlist.append(potfun(x,param))
        x=x+dx

    ymin=np.min(potlist)

    plt.plot(xlist,potlist)
    ener,wave=spy.eigh(mat)
    
    enerlist=[]
    #    wavelist=[]
    colorlist=['mediumblue','red','green','brown','orange','blueviolet']
    colorlist0=['b','g','r','c','m','y','k']
    colorlist=colorlist+colorlist0+colorlist
    
    i=kval
    wavelist=wave[:,i]
    y=ener[i]+500*wavelist
    plt.plot(xlist,y,color='b')
    plt.fill_between(xlist,ener[i],y,facecolor='b')

    subener=[]
    for x in xlist:
        subener.append(ener[i])
            
    enerlist.append(subener)  # flat line with height ener
    plt.plot(xlist,subener,color='black')
        
       
    
    plt.xlabel('x')
    plt.ylabel('Energy (meV)')
    ymax=max(potlist)
    plt.ylim([ymin,ymax])
    plt.draw()
    plt.show()

    return

def doubledelta(x0,V0,mass,xmax,N):

    a=2*xmax/(N-1)
    L=a*1.1

    dlist=np.arange(0,10,0.5)
    potfun=doublebarrier

    #    x1=x0+L
    diflist=[]
    e0list=[]
    e1list=[]
    for d in dlist:
        x0=-d
        x1=-d+L
        x2=+d
        x3=x2+L
        param=(x0,x1,x2,x3,V0,V0)
        mat=ham(xmax,N,potfun,param,mass)
        e0,e1=getener(mat,2)
        diflist.append(e1-e0)
        e0list.append(e0)
        e1list.append(e1)

    #    print(diflist)
    #    plt.scatter(dlist/a,diflist)
    plt.scatter(dlist/a,e0list)
    plt.scatter(dlist/a,e1list)

    plt.show()
    print(e0list[0]/e0list[-1])

    return


# hbar=1.05457182e-34
# xmax=30    # Tamaño de simulación, en armstrong
# N   =1000  # Resolución sensata
# x0  =0     # Dónde centramos el problema
# L   =1     # Ancho, an armstrong
# V0  =0     # Valor muy alto para simular que sea infinito
# mass=1     # Masa del electrón
# k   =float(2)  # Constante potencial harmónico

# """TAREA 1"""

# #Potencial POZO INFINITO
# # par =(x0,L,V0)
# # H = ham(xmax,N,well,par,mass)

# # ener=plotstates(H,5,xmax,N,well,par)
# # energia=[]
# # for i in range(len(ener)):
# #     energia.append(ener[i][0])

# # x=np.linspace(0,max(energia),5)
# # xo=np.linspace(0,max(energia),100)
# # P=np.polyfit(x,energia,2)
# # plt.plot(xo,np.polyval(P,xo))
# # plt.scatter(x,energia)
# # plt.show()

# #Potencial ARMÓNICO
# # par=k
# # H = ham(xmax,N,harmonic,par,mass)

# # ener=plotstates(H,5,xmax,N,harmonic,par)
# # energia=[]
# # for i in range(len(ener)):
# #     energia.append(ener[i][0])

# # x=np.linspace(0,max(energia),5)
# # xo=np.linspace(0,max(energia),100)
# # G=np.polyfit(x,energia,1)
# # plt.plot(xo,np.polyval(G,xo))
# # plt.scatter(x,energia)
# # plt.show()

# """Tarea 4"""

# emin=0
# emax=1000
# dE=2
# V0=100
# L=V0
# x0=-V0/2
# par=(x0,L,V0)
# hbar=1.054e-34

# def T(varE):
#     if np.abs(varE-V0)<10e-2:
#         T=1./(1.+0.25*4.*V0*L**(2)*mass/hbar)
#     if varE<V0:
#         k=np.sqrt(2*mass*(V0-varE)/(hbar**2)) 
#         T=1./(1.+(((V0**(2))*((np.sinh(k*L))**(2.)))/(4.*varE*(V0-varE))))
#     else:
#         k=np.sqrt(2*mass*(varE-V0)/(hbar**2))
#         T=1./(1.+(((V0**(2))*((np.sin(k*L))**(2.)))/(4.*varE*(varE-V0))))
#     return T

# plotT(xmax,N,mass,emin,emax,dE,well,par)
# # H = ham(xmax,N,well,par,mass)
# # plotstates(H,1,xmax,N,well,par)

# Ex,Ey = np.linspace(emin,emax,100),[]
# for i in Ex:
#     Ey.append(T(i))
# plt.xlabel('E(meV)')
# plt.ylabel('Transmission')
# plt.plot(Ex,Ey)

# plt.ylim([0,1])
# plt.xlabel('E(meV)')
# plt.ylabel('Transmission')
# E=[]
# for i in Ex:
#     E.append(transquare(i,L,mass,V0))
# plt.plot(Ex,E)
# plt.show()
