import axios from "axios";

const createPreauthenticatedRequestForCourse = async (axiosPrivate, url, courseId, data) => {
  const response = await axiosPrivate.post(
    `${url}?id=${courseId}`,
    {...data}
  );

  return response.data.preauthenticatedRequestUrl;
}

const createMultipartUploadUri = async (parUrl) => {
  const config = {
    method: 'put', url: parUrl, headers: {
      'opc-multipart': 'true'
    }
  };
  const response = await axios(config);
  return parUrl.split("/p/")[0] + response.data.accessUri;
}

const splitFile = (newFile) => {
  const chunkSize = 1024 * 1024;
  const chunks = [];
  for (let start = 0; start < newFile.size; start += chunkSize) {
    const chunk = newFile.slice(start, start + chunkSize);
    chunks.push(chunk);
  }
  console.log(newFile);
  console.log(chunks);
  return chunks;
}

const commit = async (url) => {
  await axios.post(url).then(() => true).catch(() => false)
}

export const uploadUtility = {
  createPreauthenticatedRequestForCourse,
  splitFile,
  createMultipartUploadUri,
  commit
}