from lightkurve import TessTargetPixelFile
import numpy as np
import matplotlib.pyplot as plt

# https://mast.stsci.edu/portal/Mashup/Clients/Mast/Portal.html      
# MAST Catalog > TESS CTL mission > Advanced Search
# Distance 12 - 24 mega parsecs
# Temperature 2000 - 3500 kelvin

# Miukulski
# https://exo.mast.stsci.edu/


# Download the pixelfile for a given star
path = input() #r"C:\Users\victor\fisicaua\tercero\SIUE\robotic_vision\project\TESS\2.fits")
tpf = TessTargetPixelFile(path)
tpf.plot(frame=40)
plt.show()

# Combine frames into lightcurve
lc = tpf.to_lightcurve(aperture_mask=tpf.pipeline_mask)
lc.plot()
plt.show()

# Flatten the curve
flat_lc = lc.flatten()
flat_lc.plot()
plt.show()

# Use a periodogram to show all the repetitive patterns, FFT
period = np.linspace(1, 5, 10000)
bls = lc.to_periodogram(method='bls', period=period, frequency_factor=500)
bls.plot()
plt.show()

# Period value corresponding to the highest peak in the periodogram
planet_x_period = bls.period_at_max_power
planet_x_t0 = bls.transit_time_at_max_power

ax = lc.fold(period=planet_x_period, epoch_time=planet_x_t0).scatter()
ax.set_xlim(-2,2)
plt.show()