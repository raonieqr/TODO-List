const express = require('express');
const app = express();
const cors = require('cors');
const EmailScheduler = require('./EmailScheduler.cjs');

app.use(cors());
app.use(express.json());

app.post('/enviar-email', async (req, res) => {
  try {
    const content = req.body.content;

    console.log("Solicitação HTTP recebida");

    const emailScheduler = new EmailScheduler(content);
    await emailScheduler.startWeeklyEmailSchedule();

    res.status(200).send('E-mail agendado com sucesso.');
  } catch (error) {
    console.error('Erro ao agendar e-mail:', error);
    
    res.status(500).send('Erro ao agendar e-mail.');
  }
});

app.listen(3000, () => {
  console.log('Servidor backend iniciado na porta 3000');
});
