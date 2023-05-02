function scaleDown(originalImage, maxWidth, maxHeight) {
  const originalWidth = originalImage.width;
  const originalHeight = originalImage.height;
  if (originalWidth > maxWidth || originalHeight > maxHeight) {
    const ratio = originalWidth / originalHeight;
    if (originalWidth > maxWidth) {
      originalWidth = maxWidth;
      originalHeight = originalWidth / ratio;
    }
    if (originalHeight > maxHeight) {
      originalHeight = maxHeight;
      originalWidth = originalHeight * ratio;
    }
  }
  const canvas = document.createElement("canvas");
  canvas.width = originalWidth;
  canvas.height = originalHeight;
  const ctx = canvas.getContext("2d");
  ctx.drawImage(originalImage, 0, 0, originalWidth, originalHeight);
  return canvas.toDataURL();
}