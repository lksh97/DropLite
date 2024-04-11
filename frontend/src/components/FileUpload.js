import React, { useState, useRef } from "react";
import axios from "axios";
import { Button, CircularProgress } from "@mui/material";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import { styled } from "@mui/material/styles";
import { isValidFileType } from "../utils/fileValidation";
import { messages, endpoints } from "../constants";
import "./FileUpload.css";

function FileUpload() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploading, setUploading] = useState(false);
  const fileInputRef = useRef(null); // Create a ref for the input element

  const handleFileSelect = (event) => {
    const file = event.target.files[0];
    if (file && isValidFileType(file)) {
      setSelectedFile(file);
    } else {
      alert(messages.unsupportedFileType);
      event.target.value = null; // Clear the file input
      setSelectedFile(null);
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) return;

    const formData = new FormData();
    formData.append("file", selectedFile);
    setUploading(true);

    try {
      await axios.post(endpoints.uploadUrl, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      alert(messages.uploadSuccess);
    } catch (error) {
      alert(messages.uploadFailure);
    } finally {
      setUploading(false);
      setSelectedFile(null); // Reset after upload
    }
  };

  const VisuallyHiddenInput = styled("input")({
    clip: "rect(0 0 0 0)",
    clipPath: "inset(50%)",
    height: 1,
    overflow: "hidden",
    position: "absolute",
    bottom: 0,
    left: 0,
    whiteSpace: "nowrap",
    width: 1,
  });

  return (
    <div className="buttons">
      <Button
        component="label"
        variant="contained"
        tabIndex={-1}
        onChange={handleFileSelect}
        disabled={uploading}
        startIcon={<CloudUploadIcon />}
      >
        Upload file
        <VisuallyHiddenInput type="file" ref={fileInputRef} />
      </Button>
      <span className="file-name">{selectedFile?.name}</span>

      <Button
        variant="contained"
        color="primary"
        onClick={handleUpload}
        disabled={!selectedFile || uploading}
        className="upload-button"
      >
        {uploading ? <CircularProgress size={24} /> : "Upload"}
      </Button>
    </div>
  );
}

export default FileUpload;
