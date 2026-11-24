<!--
Capa: Domain & Infrastructure Documentation
Responsabilidad: Documentar matemáticamente y arquitectónicamente los 6 filtros
  CUDA propios (incluyendo la distinción explícita entre EXTRA_1 y EXTRA_2).
Regla de Dependencia: Referencia técnica para los archivos .cu en
  services/ms-gpu-pycuda/app/infrastructure/cuda/kernels/.
-->
# Catálogo de los 6 Filtros CUDA Propios

1. **`MEDIA` (`mean_kernel.cu`)**: Filtro de suavizado por promedio aritmético en ventana $(2r+1)\times(2r+1)$.
2. **`NITIDEZ` (`sharpen_kernel.cu`)**: Realce de altas frecuencias ponderado por el parámetro `intensity`.
3. **`LAPLACIANO` (`laplacian_kernel.cu`)**: Operador diferencial de segundo orden $\nabla^2 I$ para detección omnidireccional de bordes.
4. **`UPS_IDENTITY` (`ups_identity_kernel.cu`)**: Efecto institucional obligatorio de la Universidad Politécnica Salesiana. Realiza mapeo cromático duotono (Azul UPS `#002855` y Dorado UPS `#F2A900`), viñeta radial y superposición alfa del sello/logo institucional (`assets/ups_logo.png`) íntegramente en GPU.
5. **`EXTRA_1` (`extra_filters_kernel.cu` -> `sobel_gradient_filter_kernel`)**: Operador no lineal de magnitud de gradiente ortogonal de Sobel $G = \sqrt{G_x^2 + G_y^2}$ (no es una máscara única lineal, sino combinación cuadrática de derivadas direccionales horizontal y vertical).
6. **`EXTRA_2` (`extra_filters_kernel.cu` -> `emboss_sepia_filter_kernel`)**: Filtro de relieve direccional (Emboss con offset de luminancia +128) combinado con transformación matricial cálida en cada hilo CUDA.
