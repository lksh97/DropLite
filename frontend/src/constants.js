export const messages = {
    uploadSuccess: "File uploaded successfully",
    uploadFailure: "Error uploading file",
    unsupportedFileType: "Unsupported file type!",
    fetchingError: "Failed to fetch files"
}

export const endpoints = {
    uploadUrl: "http://localhost:8000/upload",
    filesUrl: "http://localhost:8000/files",
    downloadUrl: (fileId) => `http://localhost:8000/download/${fileId}`
};