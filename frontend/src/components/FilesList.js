import React, { useEffect, useState } from "react";
import axios from "axios";
import "./FilesList.css";

function FileList() {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/files")
      .then((response) => {
        setFiles(response.data);
      })
      .catch((error) => {
        console.error("Error fetching files:", error);
      });
  }, []);

  const handleDownload = (filename) => {
    window.open(`http://localhost:8080/download/${filename}`);
  };

  return (
    <div>
      <h1 className="uploaded-files">Uploaded Files</h1>
      {files.length > 0 ? (
        <ul>
          {files.map((file) => (
            <li key={file.id}>
              {file.filename} - {file.filesize} bytes
              <button
                onClick={() => handleDownload(file.filename)}
                className="download-button"
              >
                Download
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p>No files uploaded yet.</p>
      )}
    </div>
  );
}

export default FileList;
