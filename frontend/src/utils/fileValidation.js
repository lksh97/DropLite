const validFileTypes = [
  "text/plain",
  "image/jpeg",
  "image/png",
  "application/json",
  "application/pdf",
];

export const isValidFileType = (file) => {
  return validFileTypes.includes(file?.type);
};
