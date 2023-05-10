def M_Jacobi(A,b):
    n = len(A)
    D = np.zeros([n,n])
    U = np.zeros([n,n])
    L = np.zeros([n,n])
    for i in range(n):
        for j in range(n):
            if i==j:
                D[i,j] = A[i,j]
            if i > j:
                L[i,j] = A[i,j]
            if i < j:
                U[i,j] = A[i,j]
    B = np.dot(inversa(D),U+L)
    return B,np.dot(inversa(D),b),max(np.abs(la.eig(B)[0]))

def M_Gauss_Seidel(A,b):
    n = len(A)
    D = np.zeros([n,n])
    U = np.zeros([n,n])
    L = np.zeros([n,n])
    for i in range(n):
        for j in range(n):
            if i==j:
                D[i,j] = A[i,j]
            if i > j:
                L[i,j] = A[i,j]
            if i < j:
                U[i,j] = A[i,j]
    B = np.dot(inversa(D-L),U)
    return B,np.dot(inversa(D-L),b),max(np.abs(la.eig(B)[0]))

def jacobi(A,b,x0,norm,error,maxi):
    B,c,re = M_Jacobi(A,b)
    if re >= 1:
        print('Error: Radio espectral mayor que uno,por tanto, el método no converge')
        return
    else:
        n = 1
        x1 = iteracion_SEL(x0,B,c)
        while n < maxi:
            if abs(la.norm(x1-x0,norm)) < error:
                break
            n += 1
            x0 = x1
            x1 = iteracion_SEL(x1,B,c)
    return x1,n,re

def gauss_seidel(A,b,x0,norm,error,maxi):
    B,c,re = M_Gauss_Seidel(A,b)
    if re >= 1:
        print('Error: Radio espectral mayor que uno,por tanto, el método no converge')
        return
    else:
        n = 1
        x1 = iteracion_SEL(x0,B,c)
        while n < maxi:
            if abs(la.norm(x1-x0,norm)) < error:
                break
            n += 1
            x0,x1 = x1,iteracion_SEL(x1,B,c)
    return x1,n,re
