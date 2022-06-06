import axios from "axios";

const createPreauthenticatedRequestForCourse = async (axiosPrivate, url, courseId, data) => {
  const response = await axiosPrivate.post(
    url.replace('{courseId}', courseId),
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
  // if file size is less than 1 GB, then chunk size is 1 MB,
  // else chunk size is 5 MB.
  let chunkSize = 1024 * 1024;
  if (newFile.size / 1024 / 1024 > 1000) {
    chunkSize = 5 * chunkSize;
  }
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
  return axios.post(url).then((res) => res).catch((err) => err)
}

export const uploadUtility = {
  createPreauthenticatedRequestForCourse,
  splitFile,
  createMultipartUploadUri,
  commit
}