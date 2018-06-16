import io
import urllib
import shutil
import json

from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
from processor import process_new_model, process_predict, process_update

address = ''
port = 2333

class MyRequestHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.process()

    def do_Post(self):
        self.process()

    def actions(self, params):
        result = "OK"
        task_id = params['task_id']
        if 'pic_path' in params.keys():
            pic_path = params['pic_path']
            result_list = process_predict(task_id=task_id, pic_path=pic_path)
            result = json.dumps(result_list)
        elif 'tag_file_path' in params.keys():
            tag_file_path = params['tag_file_path']
            process_update(task_id=task_id, tag_file_path=tag_file_path)
        elif 'pic_path_file' in params.keys():
            pic_path_file = params['pic_path_file']
            target_labels_file_path = params['target_labels_file_path']
            process_new_model(task_id=task_id, pic_path_file=pic_path_file,
                              target_labels_file_path=target_labels_file_path)
        return result

    def process(self):
        content = ""
        if '?' in self.path:
            query = urllib.splitquery(self.path)
            action = query[0] # in fact, it's useless.
            if query[1]: # load get params
                query_params = {}
                for qp in query[1].split('&'):
                    kv = qp.split('=')
                    query_params[kv[0]] = urllib.unquote(kv[1])

                print query_params

                content = self.actions(query_params)

        # encode content
        enc = "UTF-8"
        content = content.encode(enc)
        f = io.BytesIO()
        f.write(content)
        f.seek(0)
        self.send_response(200)
        self.send_header("Content-type", "text/html; charset=%s" % enc)
        self.send_header("Content-Length", str(len(content)))
        self.end_headers()
        shutil.copyfileobj(f, self.wfile)


# listen the request from Java Client

http_server = HTTPServer((address, port), MyRequestHandler)
print 'started http server...'
http_server.serve_forever()